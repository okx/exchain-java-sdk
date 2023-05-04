package com.okbchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Description {

    // very important, ensure order: d i m w
    @JsonProperty("details")
    @SerializedName("details")
    private String details;

    @JsonProperty("identity")
    @SerializedName("identity")
    private String identity;

    @JsonProperty("moniker")
    @SerializedName("moniker")
    private String moniker;

    @JsonProperty("website")
    @SerializedName("website")
    private String website;

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("details", this.details)
                .append("identity", this.identity)
                .append("website", this.website)
                .append("moniker", this.moniker)
                .toString();
    }
}
