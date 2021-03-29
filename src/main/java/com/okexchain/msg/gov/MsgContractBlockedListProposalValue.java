package com.okexchain.msg.gov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgContractBlockedListProposalValue {

    @JsonProperty("title")
    @SerializedName("title")
    private String title;


    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("contract_addresses")
    @SerializedName("contract_addresses")
    private String [] contractAddresses;


    @JsonProperty("is_added")
    @SerializedName("is_added")
    private boolean isAdded;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("title", title)
                .append("description", description)
                .append("contract_addresses", contractAddresses)
                .append("isAdded", isAdded)
                .toString();
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setContractAddresses(String[] contractAddresses) {
        this.contractAddresses = contractAddresses;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }


}