package io.okchain.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class MsgCancelOrder implements IMsg {
    @JsonProperty("OrderId")
    @SerializedName("OrderId")
    public String orderId;

    @JsonProperty("Sender")
    @SerializedName("Sender")
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
