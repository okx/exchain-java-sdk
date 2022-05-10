package com.okexchain.msg.ibc.channels.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.ibc.Height;
import com.okexchain.msg.ibc.Pagination;
import com.okexchain.msg.ibc.channels.pojo.Commitment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class PacketCommitmentsResponse {

    @JsonProperty("commitments")
    @SerializedName("commitments")
    private List<Commitment> commitments;

    @JsonProperty("pagination")
    @SerializedName("pagination")
    private Pagination pagination;

    @JsonProperty("height")
    @SerializedName("height")
    private Height height;
}
