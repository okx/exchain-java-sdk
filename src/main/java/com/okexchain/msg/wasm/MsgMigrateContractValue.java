package com.okexchain.msg.wasm;


import com.alibaba.fastjson.JSON;
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
public class MsgMigrateContractValue {

    @JsonProperty("code_id")
    @SerializedName("code_id")
    private String codeId;

    @JsonProperty("contract")
    @SerializedName("contract")
    private String contract;

    @JsonProperty("msg")
    @SerializedName("msg")
    private JSON msg;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;
}
