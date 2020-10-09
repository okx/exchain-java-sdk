package com.okexchain.msg.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgDeListProposalValue {

    @JsonProperty("base_asset")
    @SerializedName("base_asset")
    private String baseAsset;

    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("proposer")
    @SerializedName("proposer")
    private String proposer;

    @JsonProperty("quote_asset")
    @SerializedName("quote_asset")
    private String quoteAsset;

    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    public void setBaseAsset(String baseAsset) {
        this.baseAsset = baseAsset;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public void setQuoteAsset(String quoteAsset) {
        this.quoteAsset = quoteAsset;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("title", title)
            .append("description", description)
            .append("proposer", proposer)
            .append("base_asset", baseAsset)
            .append("quote_asset", quoteAsset)
            .toString();
    }
}