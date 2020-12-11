package com.okexchain.msg.ammswap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgCreateExchangeValue {

    @JsonProperty("token0_name")
    @SerializedName("token0_name")
    private String token0Name;

    @JsonProperty("token1_name")
    @SerializedName("token1_name")
    private String token1Name;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    public void setTokenNameBefore(String token0Name) { this.token0Name = token0Name; }

    public void setTokenNameAfter(String token1Name) { this.token1Name = token1Name; }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("token0_name", token0Name)
                .append("token1_name", token1Name)
                .append("sender", sender)
                .toString();
    }
}
