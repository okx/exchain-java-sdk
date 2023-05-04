package com.okbchain.msg.token;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgTokenModify extends MsgBase {

    public MsgTokenModify() {
        setMsgType("okbchain/token/MsgModify");
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