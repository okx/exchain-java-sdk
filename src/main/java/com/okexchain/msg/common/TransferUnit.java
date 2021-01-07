package com.okexchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferUnit {

    @JsonProperty("coins")
    @SerializedName("coins")
    private List<Token> coins;

    @JsonProperty("to")
    @SerializedName("to")
    private String to;

    public TransferUnit() {

    }

    public TransferUnit(String to, List<Token> coins) {
        this.to = to;
        this.coins = coins;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCoins(List<Token> coins) {
        this.coins = coins;
    }

    public List<Token> getCoins() {
        return coins;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("to", to)
                .append("coins", coins)
                .toString();
    }
}
