package com.okexchain.msg.farm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.tx.UnsignedTx;
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

        EnvInstance.getEnv().setChainID("okexchain-66");
        EnvInstance.getEnv().setRestServerUrl("https://okex.com");

        MsgCreatePool msg = new MsgCreatePool();
        msg.init("okexchain1qjktrxusyql83kk6f8yxqmpfcu8lk3cycw5aae", "");

        Message messages = msg.produceMsg(
                "0",
                "ammswap_ethk-c63_okt",
                "ethk_okt",
                "okt"
        );
        UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.05000000", "500000", "");
        JSONObject jsonpObject = JSON.parseObject(unsignedTx.toString(), Feature.OrderedField);
        jsonpObject.put("addressIndex", 82);

        System.out.println(jsonpObject.toString());
    }
}
