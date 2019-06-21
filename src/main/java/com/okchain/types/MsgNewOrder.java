package com.okchain.types;

import com.alibaba.fastjson.annotation.JSONField;

public class MsgNewOrder implements IMsg {
    public MsgNewOrder() {
    }

    public MsgNewOrder(String batchNumber, String depth, String price, String product, String quantity, String sender, String side) {
        this.batchNumber = batchNumber;
        this.depth = depth;
        this.price = price;
        this.product = product;
        this.quantity = quantity;
        this.sender = sender;
        this.side = side;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    @JSONField(name = "BatchNumber")
    private String batchNumber;

    @JSONField(name = "Depth")
    private String depth;

    @JSONField(name = "Price")
    private String price;    // price of the order

    @JSONField(name = "Product")
    private String product;   // product for trading pair in full name of the tokens

    @JSONField(name = "Quantity")
    private String quantity;     // quantity of the order

    @JSONField(name = "Sender")
    private String sender ; // order maker address

    @JSONField(name = "Side")
    private String side;  // BUY/SELL

}
