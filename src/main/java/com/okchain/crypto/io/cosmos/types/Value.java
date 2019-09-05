package com.okchain.crypto.io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-19 18:48
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Value {

    @JsonProperty("from_address")
    @SerializedName("from_address")
    private String fromAddress;

    @JsonProperty("to_address")
    @SerializedName("to_address")
    private String toAddress;

    private List<Token> amount;

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    public List<Token> getAmount() {
        return amount;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("from_address", fromAddress)
            .append("to_address", toAddress)
            .append("amount", amount)
            .toString();
    }
}
