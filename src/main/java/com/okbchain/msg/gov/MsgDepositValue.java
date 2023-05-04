package com.okbchain.msg.gov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.msg.common.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgDepositValue {

    @JsonProperty("amount")
    @SerializedName("amount")
    private List<Token> amount;

    @JsonProperty("depositor")
    @SerializedName("depositor")
    private String depositor;

    @JsonProperty("proposal_id")
    @SerializedName("proposal_id")
    private String proposalID;

    public void setProposalID(String proposalID) {
        this.proposalID = proposalID;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("proposal_id", proposalID)
                .append("depositor", depositor)
                .append("amount", amount)
                .toString();
    }
}
