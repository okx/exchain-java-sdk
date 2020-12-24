package com.okexchain.msg.order;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgCancelOrders extends MsgBase {

    public MsgCancelOrders() { setMsgType("okexchain/order/MsgCancel"); }

    public Message produceMsg(String [] orderIDs){

        MsgCancelOrdersValue value = new MsgCancelOrdersValue();
        value.setSender(this.address);
        value.setOrderIDs(orderIDs);

        Message<MsgCancelOrdersValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
