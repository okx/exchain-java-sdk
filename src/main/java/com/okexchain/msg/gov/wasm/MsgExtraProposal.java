package com.okexchain.msg.gov.wasm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.gov.UniversalProposal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MsgExtraProposal extends UniversalProposal<MsgExtraProposal.ProposalValue> {

    public MsgExtraProposal(ProposalValue proposalValue) {
        super("wasm/ExtraProposal", proposalValue);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonPropertyOrder(alphabetic = true)
    public static class ProposalValue {

        @JsonProperty("title")
        @SerializedName("title")
        private String title;

        @JsonProperty("description")
        @SerializedName("description")
        private String description;

        @JsonProperty("action")
        @SerializedName("action")
        private String action;

        @JsonProperty("extra")
        @SerializedName("extra")
        private String extra;
    }

}
