package com.okexchain.msg.ibc.connections.pojo;


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
public class Connections {

    @JsonProperty("id")
    @SerializedName("id")
    private String id;

    @JsonProperty("client_id")
    @SerializedName("client_id")
    private String clientId;

    @JsonProperty("versions")
    @SerializedName("versions")
    private List<Versions> versions;

    @JsonProperty("state")
    @SerializedName("state")
    private String state;

    @JsonProperty("counterparty")
    @SerializedName("counterparty")
    private CounterPartyPrefix counterPartyPrefix;

    @JsonProperty("delay_period")
    @SerializedName("delay_period")
    private String delayPeriod;
}
