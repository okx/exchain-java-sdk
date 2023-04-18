package com.okexchain.msg.gov;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class MsgUpgradeProposal extends UniversalProposal<MsgUpgradeProposal.ProposalValue> {
    public MsgUpgradeProposal(ProposalValue proposalValue) {
        super("okexchain/params/UpgradeProposal",proposalValue);
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

        @JsonProperty("name")
        @SerializedName("name")
        private String name;

        @JsonProperty("expectHeight")
        @SerializedName("expectHeight")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String expectHeight;

        @JsonProperty("config")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @SerializedName("config")
        private String config;
    }
}
