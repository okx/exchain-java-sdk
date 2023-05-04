package com.okbchain.msg.ibc.client;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.msg.common.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ClientStatesResponse {

    @JsonProperty("client_states")
    @SerializedName("client_states")
    private List<ClientStates> clientStates;

    @JsonProperty("pagination")
    @SerializedName("pagination")
    private Pagination pagination;
}
