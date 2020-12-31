package com.okexchain.msg.farm;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgCreatePool extends MsgBase {

    public MsgCreatePool() { setMsgType("okexchain/farm/MsgCreatePool"); }

    public Message produceMsg(String minLockAmount, String minLockDenom, String poolName, String yieldedSymbol) {

        Token minLockAmountToken = new Token();
        minLockAmountToken.setAmount(Utils.NewDecString(minLockAmount));
        minLockAmountToken.setDenom(minLockDenom);

        MsgCreatePoolValue value = new MsgCreatePoolValue();

        value.setMinLockAmount(minLockAmountToken);
        value.setOwner(this.address);
        value.setPoolName(poolName);
        value.setYieldedSymbol(yieldedSymbol);

        Message<MsgCreatePoolValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {

        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgCreatePool msg = new MsgCreatePool();
        msg.init(key);

        Message messages = msg.produceMsg(
                "100",
                "okt",
                "turing_pool",
                "okt"
        );
        JSONObject res = msg.submit(messages, "0.05", "500000", "create pool!");

        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
