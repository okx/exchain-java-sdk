package com.okexchain.msg.dex;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgDeposit extends MsgBase {

    public MsgDeposit() { setMsgType("okexchain/dex/MsgDeposit"); }

    public Message produceMsg(String denom, String amount, String product) {

        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amount));

        MsgDepositValue value = new MsgDepositValue();

        value.setAmount(token);
        value.setDepositor(this.address);
        value.setProduct(product);

        Message<MsgDepositValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {

        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgDeposit msg = new MsgDeposit();
        msg.init(key);

        Message messages = msg.produceMsg(
                "okt",
                "500",
                "usdt-d60_okt"
        );
        JSONObject res = msg.submit(messages, "0.05", "500000", "dex deposit!");

        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}