package io.cosmos.msg;

import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgConfirmOwnershipValue;

public class MsgConfirmOwnership extends MsgBase{

    public MsgConfirmOwnership() { setMsgType("okexchain/token/MsgConfirmOwnership"); }

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