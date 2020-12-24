package com.okexchain.msg.order;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

import java.util.List;

public class MsgNewOrders extends MsgBase {

    public MsgNewOrders() { setMsgType("okexchain/order/MsgNew"); }

    public Message produceMsg(List<OrderItem> orderItems) {
        MsgNewOrdersValue value = new MsgNewOrdersValue();
        value.setSender(this.address);
        value.setNewOrderItem(orderItems);

        Message<MsgNewOrdersValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
