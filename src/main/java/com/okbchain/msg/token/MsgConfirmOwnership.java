package com.okbchain.msg.token;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgConfirmOwnership extends MsgBase {

    public MsgConfirmOwnership() { setMsgType("okbchain/token/MsgConfirmOwnership"); }

    public Message produceConfirmOwnershipMsg(String address, String symbol) {

        MsgConfirmOwnershipValue value = new MsgConfirmOwnershipValue();
        value.setAddress(address);
        value.setSymbol(symbol);

        Message<MsgConfirmOwnershipValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }

}