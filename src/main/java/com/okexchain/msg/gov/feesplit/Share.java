package com.okexchain.msg.gov.feesplit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Share {

    @JsonProperty("contract_addr")
    @SerializedName("contract_addr")
    private String contractAddr;

    @JsonProperty("share")
    @SerializedName("share")
    private String share;

    //overwrite set method
    public void setShare(double share) {
        this.share = Utils.NewDecString(String.valueOf(share));
    }
}

