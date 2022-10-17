package com.okexchain.msg.wasm;


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
public class MsgUpdateAdminValue {


    @JsonProperty("new_admin")
    @SerializedName("new_admin")
    private String newAdmin;


    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    @JsonProperty("contract")
    @SerializedName("contract")
    private String contract;
}
