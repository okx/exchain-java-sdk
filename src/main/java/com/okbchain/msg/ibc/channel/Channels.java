package com.okbchain.msg.ibc.channel;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.msg.common.CounterParty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Channels {

    @JsonProperty("state")
    @SerializedName("state")
    private String state;

    @JsonProperty("ordering")
    @SerializedName("ordering")
    private String ordering;

    @JsonProperty("counterparty")
    @SerializedName("counterparty")
    private CounterParty counterParty;

    @JsonProperty("connection_hops")
    @SerializedName("connection_hops")
    private List<String> connectionHops;

    @JsonProperty("version")
    @SerializedName("version")
    private String version;

    @JsonProperty("port_id")
    @SerializedName("port_id")
    private String portId;

    @JsonProperty("channel_id")
    @SerializedName("channel_id")
    private String channelId;
}
