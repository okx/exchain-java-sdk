package io.okexchain.types;

public class MultiNewOrderItem {
    // price of the order
    private String price;

    // product for trading pair in full name of the tokens
    private String product;

    // quantity of the order
    private String quantity;

    // BUY/SELL
    private String side;

    public MultiNewOrderItem(String price, String product, String quantity, String side) {
        this.price = price;
        this.product = product;
        this.quantity = quantity;
        this.side = side;
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
}
