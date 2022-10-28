package com.okexchain.msg.gov.wasm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class BlockedMethod {


    @JsonProperty("contractAddr")
    @SerializedName("contractAddr")
    private String contractAddr;

    @JsonProperty("methods")
    @SerializedName("methods")
    private List<Method> methodList;

}
