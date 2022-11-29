package com.okexchain.msg.reward;


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
public class MsgWithdrawDelegatorRewardValue {


    @JsonProperty("delegator_address")
    @SerializedName("delegator_address")
    private String delegatorAddress;


    @JsonProperty("validator_address")
    @SerializedName("validator_address")
    private String validatorAddress;
}
