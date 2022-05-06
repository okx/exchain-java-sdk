package com.okexchain.msg.ibc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ParamsResponse {

    @JsonProperty("params")
    @SerializedName("params")
    private Params params;

    public ParamsResponse() {
    }

    public ParamsResponse(Params params) {
        this.params = params;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ParamsResponse{" +
                "params=" + params +
                '}';
    }
}
