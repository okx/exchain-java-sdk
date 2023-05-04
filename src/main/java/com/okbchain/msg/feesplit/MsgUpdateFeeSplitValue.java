package com.okbchain.msg.feesplit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgUpdateFeeSplitValue {

    @JsonProperty("contract_address")
    @SerializedName("contract_address")
    private String contractAddress;

    @JsonProperty("deployer_address")
    @SerializedName("deployer_address")
    private String deployerAddress;

    @JsonProperty("withdrawer_address")
    @SerializedName("withdrawer_address")
    private String withdrawerAddress;

    //if contract address is ex address then convert this contract address to 0x address
    public void setContractAddress(String contractAddress) {
        try {
            this.contractAddress = Utils.convertContractAddress(contractAddress);
        } catch (Exception e) {
            log.error("address error: {}",e);
        }
    }
}
