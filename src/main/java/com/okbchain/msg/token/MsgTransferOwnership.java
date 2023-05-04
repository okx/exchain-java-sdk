package com.okbchain.msg.token;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgTransferOwnership extends MsgBase {

    public MsgTransferOwnership() { setMsgType("okbchain/token/MsgTransferOwnership");}

    public Message produceTransferOwnerShipMsg(String fromAddress, String toAddress, String symbol) {

        MsgTransferOwnershipValue value = new MsgTransferOwnershipValue();
        value.setFromAddress(fromAddress);
        value.setToAddress(toAddress);
        value.setSymbol(symbol);

        Message<MsgTransferOwnershipValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }

}