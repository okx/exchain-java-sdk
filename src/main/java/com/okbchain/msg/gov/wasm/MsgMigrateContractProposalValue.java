package com.okbchain.msg.gov.wasm;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.utils.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgMigrateContractProposalValue {


    @JsonProperty("title")
    @SerializedName("title")
    private String title;


    @JsonProperty("description")
    @SerializedName("description")
    private String description;


    @JsonProperty("contract")
    @SerializedName("contract")
    private String contract;


    @JsonProperty("code_id")
    @SerializedName("code_id")
    private String codeId;


    @JsonProperty("msg")
    @SerializedName("msg")
    private JSON msg;


    public void setCodeId(int codeId) {
        this.codeId = String.valueOf(codeId);
    }

    public void setMsg(String msgJson) throws Exception {
        this.msg = Utils.getSortJson(msgJson);
    }
}
