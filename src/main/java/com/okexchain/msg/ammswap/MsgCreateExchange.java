package com.okexchain.msg.ammswap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
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
        EnvInstance.getEnv().setChainID("okexchain-66");
        EnvInstance.getEnv().setRestServerUrl("https://okexbeta.bafang.com");

        MsgCreateExchange msg = new MsgCreateExchange();
        msg.init("okexchain1362tzaqyzagzdfn5n57j7rcqt6tvu0wvuq5z4f", "");

        Message messages = msg.productMsg(
                "okb-c4d",
                "usdt-a2b"
        );

        UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.05000000", "500000", "");
        JSONObject jsonpObject = JSON.parseObject(unsignedTx.toString(), Feature.OrderedField);
        jsonpObject.put("addressIndex", 70);

        System.out.println(jsonpObject.toString());
    }
}
