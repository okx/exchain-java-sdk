package com.okexchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.Block_methods;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Contract_addresses {

    @JsonProperty("address")
    @SerializedName("address")
    private String address;

    @JsonProperty("block_methods")
    @SerializedName("block_methods")
    private List<Block_methods> block_methods;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("address", address)
                .append("block_methods", block_methods)
                .toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }

    public void setBlock_methods(List<Block_methods> block_methods) {
        this.block_methods = block_methods;
    }
    public List<Block_methods> getBlock_methods() {
        return block_methods;
    }
}
