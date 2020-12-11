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
public class MsgRemoveLiquidityValue {

    @JsonProperty("deadline")
    @SerializedName("deadline")
    private int deadline;

    @JsonProperty("liquidity")
    @SerializedName("liquidity")
    private String liquidity;

    private Token minBaseAmount;

    private Token minQuoteAmount;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    public void setDeadline(int deadline) { this.deadline = deadline; }

    public void setLiquidity(String liquidity) {
        this.liquidity = liquidity;
    }

    public void setMinBaseAmount(Token minBaseAmount) {
        this.minBaseAmount = minBaseAmount;
    }

    public void setMinQuoteAmount(Token minQuoteAmount) { this.minQuoteAmount = minQuoteAmount; }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("liquidity", liquidity)
                .append("min_base_amount", minBaseAmount)
                .append("min_quote_amount", minQuoteAmount)
                .append("deadline", deadline)
                .append("sender", sender)
                .toString();
    }
}
