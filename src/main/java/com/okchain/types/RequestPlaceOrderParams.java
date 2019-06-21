package com.okchain.types;

import com.alibaba.fastjson.annotation.JSONField;

public class RequestPlaceOrderParams {
    @JSONField(name = "Price")
    private String price;    // price of the order

    @JSONField(name = "Product")
    private String product;   // product for trading pair in full name of the tokens

    @JSONField(name = "Quantity")
    private String quantity;     // quantity of the order

    @JSONField(name = "Side")
    private String side;  // BUY/SELL

    public RequestPlaceOrderParams(String price, String product, String quantity, String side) {
        this.price = price;
        this.product = product;
        this.quantity = quantity;
        this.side = side;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
