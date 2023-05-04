package com.okbchain.msg.gov;


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
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgManageSysContractAddressProposalValue {


    @JsonProperty("title")
    @SerializedName("title")
    private String title;


    @JsonProperty("description")
    @SerializedName("description")
    private String description;


    @JsonProperty("contract_address")
    @SerializedName("contract_address")
    private String contractAddr;


    @JsonProperty("is_added")
    @SerializedName("is_added")
    private boolean isAdded;

    // if address is Hex address then convert From Hex To ExBech32
    public void setContractAddr(String contractAddress) {
        try {
            this.contractAddr = Utils.convertHexAddrToExBech32(contractAddress);
        } catch (Exception e) {
            log.error("address error: {}",e);
        }
    }
}
