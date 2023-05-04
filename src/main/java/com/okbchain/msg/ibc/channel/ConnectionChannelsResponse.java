package com.okbchain.msg.ibc.channel;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.msg.common.Height;
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
public class ConnectionChannelsResponse {

    @JsonProperty("channels")
    @SerializedName("channels")
    private List<Channels> channels;

    @JsonProperty("pagination")
    @SerializedName("pagination")
    private Pagination pagination;

    @JsonProperty("height")
    @SerializedName("height")
    private Height height;
}
