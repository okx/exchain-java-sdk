package com.okexchain.msg.gov.wasm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgUpdateAdminProposalValue {


    @JsonProperty("title")
    @SerializedName("title")
    private String title;


    @JsonProperty("description")
    @SerializedName("description")
    private String description;


    @JsonProperty("contract")
    @SerializedName("contract")
    private String contract;
}
