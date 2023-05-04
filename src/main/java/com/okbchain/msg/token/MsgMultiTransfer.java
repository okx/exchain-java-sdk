package com.okbchain.msg.token;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.TransferUnit;

import java.util.List;

public class MsgMultiTransfer extends MsgBase {

    public MsgMultiTransfer() { setMsgType("okbchain/token/MsgMultiTransfer"); }

    public Message produceMsg(List<TransferUnit> transfers) {

        MsgMultiTransferValue value = new MsgMultiTransferValue();

        value.setFrom(this.address);
        value.setTransfers(transfers);

        Message<MsgMultiTransferValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
