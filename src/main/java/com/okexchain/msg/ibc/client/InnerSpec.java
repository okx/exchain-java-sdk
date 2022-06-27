package com.okexchain.msg.ibc.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class InnerSpec {

    @JsonProperty("child_order")
    @SerializedName("child_order")
    private List<Integer> childOrder;

    @JsonProperty("child_size")
    @SerializedName("child_size")
    private int childSize;

    @JsonProperty("min_prefix_length")
    @SerializedName("min_prefix_length")
    private int minPrefixLength;

    @JsonProperty("max_prefix_length")
    @SerializedName("max_prefix_length")
    private int maxPrefixLength;

    @JsonProperty("empty_child")
    @SerializedName("empty_child")
    private String emptyChild;

    @JsonProperty("hash")
    @SerializedName("hash")
    private String hash;
}