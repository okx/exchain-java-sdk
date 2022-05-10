package com.okexchain.msg.ibc.connections.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ProofSpecs {

    @JsonProperty("leaf_spec")
    @SerializedName("leaf_spec")
    private LeafSpec leafSpecpec;


    @JsonProperty("inner_spec")
    @SerializedName("inner_spec")
    private InnerSpec innerSpec;


    @JsonProperty("max_depth")
    @SerializedName("max_depth")
    private int maxDepth;


    @JsonProperty("min_depth")
    @SerializedName("min_depth")
    private int minDepth;
}
