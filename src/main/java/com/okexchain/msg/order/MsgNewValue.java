package com.okexchain.msg.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.NewOrderItem;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgNewValue {

    private List<NewOrderItem> orderItem;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    public List<NewOrderItem> getNewOrderItem() {
        return orderItem;
    }

    public void setNewOrderItem(List<NewOrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sender", sender)
                .append("order_items", orderItem)
                .toString();
    }
}
