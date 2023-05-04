package com.okbchain.msg.gov.wasm;


import com.fasterxml.jackson.annotation.*;
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
    private List<Method> methods;

}
