package com.okchain.types;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class MsgMultiCancelOrder implements IMsg {
    @JSONField(name = "order_ids")
    private List<String> orderIdItems;

    // order maker address
    private String sender;

    public MsgMultiCancelOrder(String sender, List<String> orderIdItems) {
        this.sender = sender;
        this.orderIdItems = orderIdItems;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getOrderIdItems() {
        return orderIdItems;
    }

    public void setOrderIdItems(List<String> orderIdItems) {
        this.orderIdItems = orderIdItems;
    }
}
