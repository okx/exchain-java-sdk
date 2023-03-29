package com.okexchain.msg.gov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MsgModifyNextBlockUpdateProposal extends UniversalProposal<MsgModifyNextBlockUpdateProposal.ProposalValue> {
    public MsgModifyNextBlockUpdateProposal(ProposalValue proposalValue) {
        super("okexchain/mint/ModifyNextBlockUpdateProposal", proposalValue);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonPropertyOrder(alphabetic = true)
    static class ProposalValue {

        @JsonProperty("title")
        @SerializedName("title")
        private String title;

        @JsonProperty("description")
        @SerializedName("description")
        private String description;

        @JsonProperty("block_num")
        @SerializedName("block_num")
        private String blockNum;
    }

}
