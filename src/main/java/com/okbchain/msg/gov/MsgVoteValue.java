package com.okbchain.msg.gov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgVoteValue {

    @JsonProperty("option")
    @SerializedName("option")
    private String option;

    @JsonProperty("proposal_id")
    @SerializedName("proposal_id")
    private String proposalID;

    @JsonProperty("voter")
    @SerializedName("voter")
    private String voter;

    public void setOption(String option) {
        this.option = option;
    }

    public void setProposalID(String proposalID) {
        this.proposalID = proposalID;
    }

    public void setVoters(String voter) {
        this.voter = voter;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("proposal_id", proposalID)
            .append("voter", voter)
            .append("option", option)
            .toString();
    }
}
