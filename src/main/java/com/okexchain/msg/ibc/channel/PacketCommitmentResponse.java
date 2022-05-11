package com.okexchain.msg.ibc.channel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.Height;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class PacketCommitmentResponse {
    @JsonProperty("commitment")
    @SerializedName("commitment")
    private String commitment;


    @JsonProperty("proof")
    @SerializedName("proof")
    private String proof;


    @JsonProperty("proof_height")
    @SerializedName("proof_height")
    private Height height;
}
