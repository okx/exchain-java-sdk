package com.okexchain.msg.ibc.connection;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.Height;
import com.okexchain.msg.common.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ConnectionsResponse {


    @JsonProperty("connections")
    @SerializedName("connections")
    private List<Connections> connections;


    @JsonProperty("pagination")
    @SerializedName("pagination")
    private Pagination pagination;


    @JsonProperty("height")
    @SerializedName("height")
    private Height height;
}
