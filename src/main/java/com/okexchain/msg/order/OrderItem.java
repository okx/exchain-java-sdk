package com.okexchain.msg.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.utils.Utils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class OrderItem {

    @JsonProperty("price")
    @SerializedName("price")
    private String price;

    @JsonProperty("product")
    @SerializedName("product")
    private String product;

    @JsonProperty("quantity")
    @SerializedName("quantity")
    private String quantity;

    @JsonProperty("side")
    @SerializedName("side")
    private String side;

    public OrderItem() {

    }

    public OrderItem(String price, String product, String quantity, String side) {
        this.price = Utils.NewDecString(price);
        this.product = product;
        this.quantity = Utils.NewDecString(quantity);
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("product", product)
                .append("side", side)
                .append("price", price)
                .append("quantity", quantity)
                .toString();
    }
}
