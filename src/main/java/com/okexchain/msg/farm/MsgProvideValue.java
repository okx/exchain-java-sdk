package com.okexchain.msg.farm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgProvideValue {

    @JsonProperty("address")
    @SerializedName("address")
    private String address;

    @JsonProperty("amount")
    @SerializedName("amount")
    private Token amount;

    @JsonProperty("amount_yielded_per_block")
    @SerializedName("amount_yielded_per_block")
    private String amountYieldedPerBlock;

    @JsonProperty("pool_name")
    @SerializedName("pool_name")
    private String poolName;

    @JsonProperty("start_height_to_yield")
    @SerializedName("start_height_to_yield")
    private String startHeightToYield;

    public void setAddress(String address) { this.address = address; }

    public void setAmount(Token amount) { this.amount = amount; }

    public void setAmountYieldedPerBlock(String amountYieldedPerBlock) { this.amountYieldedPerBlock = amountYieldedPerBlock; }

    public void setPoolName(String poolName) { this.poolName = poolName; }

    public void setStartHeightToYield(String startHeightToYield) { this.startHeightToYield = startHeightToYield; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("address", address)
                .append("amount", amount)
                .append("amount_yielded_per_block", amountYieldedPerBlock)
                .append("pool_name", poolName)
                .append("start_height_to_yield", startHeightToYield)
                .toString();
    }
}
