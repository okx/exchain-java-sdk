package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

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
}
