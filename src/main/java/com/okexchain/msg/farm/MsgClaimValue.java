package com.okexchain.msg.farm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgClaimValue {

    @JsonProperty("address")
    @SerializedName("address")
    private String address;

    @JsonProperty("pool_name")
    @SerializedName("pool_name")
    private String poolName;

    public void setAddress(String address) { this.address = address; }

    public void setPoolName(String poolName) { this.poolName = poolName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("address", address)
                .append("pool_name", poolName)
                .toString();
    }
}
