package com.okexchain.legacy.types;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class MsgMultiNewOrder implements IMsg {
    @JSONField(name = "order_items")
    private List<MultiNewOrderItem> orderItems;

    // order maker address
    private String sender;



    public MsgMultiNewOrder(String sender, List<MultiNewOrderItem> orderItems) {
        this.sender = sender;
        this.orderItems = orderItems;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<MultiNewOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<MultiNewOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
