package com.okexchain.msg.order;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.NewOrderItem;
import com.okexchain.msg.order.MsgNewValue;

import java.util.ArrayList;
import java.util.List;

public class MsgNew extends MsgBase {

    public MsgNew() { setMsgType("okexchain/order/MsgNew"); }

    public Message produceMsg(String product, String side, String price, String quantity) {

        List<NewOrderItem> orderItems = new ArrayList<>();
        NewOrderItem item = new NewOrderItem();
        item.setPrice(price);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setSide(side);

        MsgNewValue value = new MsgNewValue();
        value.setSender(this.address);
        value.setNewOrderItem(orderItems);

        Message<MsgNewValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
