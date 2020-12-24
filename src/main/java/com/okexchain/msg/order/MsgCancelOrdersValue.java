package com.okexchain.msg.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgCancelOrdersValue {

    @JsonProperty("order_ids")
    @SerializedName("order_ids")
    private String [] orderIDs;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    public void setOrderIDs(String [] orderIDs) {
        this.orderIDs = orderIDs;
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
                .append("order_ids", orderIDs)
                .toString();
    }
}
