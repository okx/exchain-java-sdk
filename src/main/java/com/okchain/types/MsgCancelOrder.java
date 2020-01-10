package com.okchain.types;

import com.alibaba.fastjson.annotation.JSONField;

public class MsgCancelOrder implements IMsg {
    @JSONField(name = "order_id")
    public String orderId;

    public String sender;

    public MsgCancelOrder(String sender, String orderId) {
        this.sender = sender;
        this.orderId = orderId;
    }

    public MsgCancelOrder() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
