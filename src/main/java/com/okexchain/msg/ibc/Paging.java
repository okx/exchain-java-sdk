package com.okexchain.msg.ibc;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * request  Paging parameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Paging {


    //Request parameters
    @JsonProperty("key")
    @SerializedName("key")
    private String key;

    //Request parameters
    @JsonProperty("offset")
    @SerializedName("offset")
    private int offset;


    //Request parameters
    @JsonProperty("limit")
    @SerializedName("limit")
    private int limit;

    //Request parameters
    @JsonProperty("count_total")
    @SerializedName("count_total")
    private boolean countTotal;
}
