package com.okexchain.msg.wasm;



import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgStoreCodeValue {


    @JsonProperty("instantiate_permission")
    @SerializedName("instantiate_permission")
    private JSON instantiatePermission;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    @JsonProperty("wasm_byte_code")
    @SerializedName("wasm_byte_code")
    private String wasmByteCode;
}
