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
public class MsgCreatePoolValue {

    @JsonProperty("min_lock_amount")
    @SerializedName("min_lock_amount")
    private Token minLockAmount;

    @JsonProperty("owner")
    @SerializedName("owner")
    private String owner;

    @JsonProperty("pool_name")
    @SerializedName("pool_name")
    private String poolName;

    @JsonProperty("yielded_symbol")
    @SerializedName("yielded_symbol")
    private String yieldedSymbol;

    public void setMinLockAmount(Token minLockAmount) {
        this.minLockAmount = minLockAmount;
    }

    public void setOwner(String owner) { this.owner = owner; }

    public void setPoolName(String poolName) { this.poolName = poolName; }

    public void setYieldedSymbol(String yieldedSymbol) { this.yieldedSymbol = yieldedSymbol; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("min_lock_amount", minLockAmount)
                .append("owner", owner)
                .append("pool_name", poolName)
                .append("yielded_symbol", yieldedSymbol)
                .toString();
    }
}
