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
public class ContractAddress {

    @JsonProperty("address")
    @SerializedName("address")
    private String address;

    @JsonProperty("blockMethod")
    @SerializedName("blockMethod")
    private List<BlockMethod> blockMethod;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("address", address)
                .append("blockMethod", blockMethod)
                .toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBlock_methods(List<BlockMethod> blockMethod) {
        this.blockMethod = blockMethod;
    }

    public String getAddress(){
        return address;
    }

    public List<BlockMethod> getBlockMethod(){
        return blockMethod;
    }
}
