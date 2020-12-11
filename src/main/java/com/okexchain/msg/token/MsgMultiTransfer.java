package com.okexchain.msg.token;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.TransferUnits;
import com.okexchain.msg.token.MsgMultiTransferValue;

import java.util.List;

public class MsgMultiTransfer extends MsgBase {

    public MsgMultiTransfer() { setMsgType("okexchain/token/MsgMultiTransfer"); }

    public Message produceMsg(List<TransferUnits> transfers) {

        MsgMultiTransferValue value = new MsgMultiTransferValue();

        value.setFrom(this.address);
        value.setTransferUnits(transfers);

        Message<MsgMultiTransferValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
