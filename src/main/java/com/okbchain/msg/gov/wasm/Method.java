package com.okbchain.msg.gov.wasm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Method {


    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("extra")
    @SerializedName("extra")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String extra;
}
