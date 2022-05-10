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
public class LeafSpec {

    @JsonProperty("hash")
    @SerializedName("hash")
    private String hash;

    @JsonProperty("prehash_key")
    @SerializedName("prehash_key")
    private String prehashKey;

    @JsonProperty("prehash_value")
    @SerializedName("prehash_value")
    private String prehashValue;

    @JsonProperty("length")
    @SerializedName("length")
    private String length;

    @JsonProperty("prefix")
    @SerializedName("prefix")
    private String prefix;
}
