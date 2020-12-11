package com.okexchain.msg.order;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.order.MsgCancelValue;

public class MsgCancel extends MsgBase {

    public MsgCancel() { setMsgType("okexchain/order/MsgCancel"); }

    public Message produceMsg(String [] orderIDs){

        MsgCancelValue value = new MsgCancelValue();
        value.setSender(this.address);
        value.setOrderIDs(orderIDs);

        Message<MsgCancelValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
