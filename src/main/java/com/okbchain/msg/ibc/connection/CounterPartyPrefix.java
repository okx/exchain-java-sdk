package com.okbchain.msg.ibc.connection;

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
public class CounterPartyPrefix {
    @JsonProperty("client_id")
    @SerializedName("client_id")
    private String clientId;

    @JsonProperty("connection_id")
    @SerializedName("connection_id")
    private String connectionId;

    @JsonProperty("prefix")
    @SerializedName("prefix")
    private Prefix prefix;
}
