package com.okexchain.msg.ammswap;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgCreateExchange extends MsgBase {

    public MsgCreateExchange() { setMsgType("okexchain/ammswap/MsgCreateExchange"); }

    public Message productMsg(String token0Name, String token1Name) {

        MsgCreateExchangeValue value = new MsgCreateExchangeValue();

        value.setSender(this.address);
        value.setTokenNameBefore(token0Name);
        value.setTokenNameAfter(token1Name);

        Message<MsgCreateExchangeValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgCreateExchange msg = new MsgCreateExchange();
        msg.init(key);

        Message messages = msg.productMsg(
                "usdk-5f7",
                "okt"
        );
        JSONObject res = msg.submit(messages, "0.05", "500000", "create exchange!");

        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
