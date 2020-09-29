package com.okexchain.msg.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgAddSharesValue {
    @JsonProperty("delegator_address")
    @SerializedName("delegator_address")
    private String delAddr;

    @JsonProperty("validator_addresses")
    @SerializedName("validator_addresses")
    private String [] valAddrs;

    public void setDelAddr(String delAddr) {
        this.delAddr = delAddr;
    }

    public void setValAddrs(String [] valAddrs) {
        this.valAddrs = valAddrs;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("delegator_address", this.delAddr)
                .append("validator_addresses", this.valAddrs)
                .toString();
    }

}

