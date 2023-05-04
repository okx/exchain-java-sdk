package com.okbchain.msg.ibc.connection;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.msg.common.Height;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ClientConnectionsResponse {

    @JsonProperty("connection_paths")
    @SerializedName("connection_paths")
    private List<String> connectionPaths;

    @JsonProperty("proof")
    @SerializedName("proof")
    private String proof;

    @JsonProperty("proof_height")
    @SerializedName("proof_height")
    private Height proofHeight;
}
