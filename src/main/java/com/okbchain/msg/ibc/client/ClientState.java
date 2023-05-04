package com.okbchain.msg.ibc.client;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.msg.common.Height;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ClientState {

    @JsonProperty("@type")
    @SerializedName("@type")
    private String type;

    @JsonProperty("chain_id")
    @SerializedName("chain_id")
    private String chainId;

    @JsonProperty("trust_level")
    @SerializedName("trust_level")
    private TrustLevel trustLevel;

    @JsonProperty("trusting_period")
    @SerializedName("trusting_period")
    private String trustingPeriod;

    @JsonProperty("unbonding_period")
    @SerializedName("unbonding_period")
    private String unbondingPeriod;

    @JsonProperty("max_clock_drift")
    @SerializedName("max_clock_drift")
    private String maxClockDrift;

    @JsonProperty("frozen_height")
    @SerializedName("frozen_height")
    private Height frozenHeight;

    @JsonProperty("latest_height")
    @SerializedName("latest_height")
    private Height latestHeight;

    @JsonProperty("proof_specs")
    @SerializedName("proof_specs")
    private List<ProofSpecs> proofSpecs;

    @JsonProperty("upgrade_path")
    @SerializedName("upgrade_path")
    private List<String> upgradePath;

    @JsonProperty("allow_update_after_expiry")
    @SerializedName("allow_update_after_expiry")
    private boolean allowUpdateAfterExpiry;

    @JsonProperty("allow_update_after_misbehaviour")
    @SerializedName("allow_update_after_misbehaviour")
    private boolean allowUpdateAfterMisbehaviour;
}