package com.okbchain.msg.reward;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgEditValidatorCommissionRateValue {


    @JsonProperty("commission_rate")
    @SerializedName("commission_rate")
    private String commissionRate;

    @JsonProperty("address")
    @SerializedName("address")
    private String validatorAddress;


    public void setCommissionRate(BigDecimal commissionRate) throws Exception {
        int n1 = commissionRate.compareTo(new BigDecimal(0));
        int n2 = commissionRate.compareTo(new BigDecimal(1));
        if ((n1 == 0 || n1 == 1) && (n2 == -1 || n2 == 0)) {
            this.commissionRate = Utils.NewDecString(String.valueOf(commissionRate));
        } else {
            throw new Exception("data range is [0,1]");
        }
    }
}
