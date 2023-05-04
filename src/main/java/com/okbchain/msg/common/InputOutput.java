package com.okbchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class InputOutput {

    @JsonProperty("address")
    @SerializedName("address")
    private String address;

    @JsonProperty("coins")
    @SerializedName("coins")
    private List<Token> coins;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Token> getCoins() {
        return coins;
    }

    public void setCoins(List<Token> coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("address", address)
            .append("coins", coins)
            .toString();
    }
}
