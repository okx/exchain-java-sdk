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
public class Fee {

    @JsonProperty("amount")
    @SerializedName("amount")
    private List<Token> amount;

    @JsonProperty("gas")
    @SerializedName("gas")
    private String gas;

    public List<Token> getAmount() {
        return amount;
    }

    public String getGas() {
        return gas;
    }

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("amount", amount)
            .append("gas", gas)
            .toString();
    }
}
