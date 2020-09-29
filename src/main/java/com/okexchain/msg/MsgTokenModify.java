package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgTokenModifyValue;

public class MsgTokenModify extends MsgBase {

    public MsgTokenModify() {
        setMsgType("okexchain/token/MsgModify");
    }

    public Message produceTokenModifyMsg (String description, boolean isDescEdit, String owner, String symbol, String wholeName, boolean isWholeNameEdit) {

        MsgTokenModifyValue value = new MsgTokenModifyValue();
        value.setDescription(description);
        value.setDescriptionModified(isDescEdit);
        value.setOwner(owner);
        value.setSymbol(symbol);
        value.setWholeName(wholeName);
        value.setWholeNameModified(isWholeNameEdit);

        Message<MsgTokenModifyValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;

    }
}