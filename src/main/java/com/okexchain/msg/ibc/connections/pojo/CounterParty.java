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
public class CounterParty {

    @JsonProperty("port_id")
    @SerializedName("port_id")
    private String portId;

    @JsonProperty("channel_id")
    @SerializedName("channel_id")
    private String channelId;
}
