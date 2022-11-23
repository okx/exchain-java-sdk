package com.okexchain.msg.reward;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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


    public void setCommissionRate(float commissionRate) throws Exception {
        if(0f<=commissionRate&&commissionRate<=1f){
            this.commissionRate= Utils.NewDecString(String.valueOf(commissionRate));
        }else {
            throw new Exception("data range is [0,1]");
        }
    }
}
