package com.okexchain.msg.ammswap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgSwapTokenValue {

    @JsonProperty("deadline")
    @SerializedName("deadline")
    private String deadline;

    @JsonProperty("min_bought_token_amount")
    @SerializedName("min_bought_token_amount")
    private Token minBoughtTokenAmount;

    @JsonProperty("recipient")
    @SerializedName("recipient")
    private String recipient;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    @JsonProperty("sold_token_amount")
    @SerializedName("sold_token_amount")
    private Token soldTokenAmount;

    public void setDeadline(String deadline) { this.deadline = deadline; }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setMinBoughtTokenAmount(Token minBoughtTokenAmount) {
        this.minBoughtTokenAmount = minBoughtTokenAmount;
    }

    public void setSoldTokenAmount(Token soldTokenAmount) { this.soldTokenAmount = soldTokenAmount; }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sold_token_amount", soldTokenAmount)
                .append("min_bought_token_amount", minBoughtTokenAmount)
                .append("deadline", deadline)
                .append("recipient", recipient)
                .append("sender", sender)
                .toString();
    }
}
