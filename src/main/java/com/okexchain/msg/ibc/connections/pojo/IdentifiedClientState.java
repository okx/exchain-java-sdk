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
public class IdentifiedClientState {


    @JsonProperty("client_id")
    @SerializedName("client_id")
    private String clientId;

    @JsonProperty("client_state")
    @SerializedName("client_state")
    private ClientState clientState;
}
