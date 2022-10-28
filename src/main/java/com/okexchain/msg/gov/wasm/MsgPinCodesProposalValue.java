package com.okexchain.msg.gov.wasm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.ibc.transfer.ParamsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgPinCodesProposalValue {


    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("code_ids")
    @SerializedName("code_ids")
    private int[] codeIds;


    public void setCodeIds(int[] codeIds) throws Exception {
        if(codeIds.length>0){
            this.codeIds=codeIds;
        }else{
            throw new Exception("The codeIds array must be greater than 0");
        }
    }
}
