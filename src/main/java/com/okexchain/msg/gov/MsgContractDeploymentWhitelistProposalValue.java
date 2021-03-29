package com.okexchain.msg.gov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgContractDeploymentWhitelistProposalValue {

    @JsonProperty("title")
    @SerializedName("title")
    private String title;


    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("distributor_addresses")
    @SerializedName("distributor_addresses")
    private String[] distributorAddresses;


    @JsonProperty("is_added")
    @SerializedName("is_added")
    private boolean isAdded;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("title", title)
                .append("description", description)
                .append("distributorAddresses", distributorAddresses)
                .append("isAdded", isAdded)
                .toString();
    }



    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setDistributorAddresses(String[] distributorAddresses) {
        this.distributorAddresses = distributorAddresses;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }

}