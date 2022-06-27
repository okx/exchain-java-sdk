package com.okexchain.msg.ibc.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.DenomTrace;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class DenomTraceResponse {

    @JsonProperty("denom_trace")
    @SerializedName("denom_trace")
    private DenomTrace denomTrace;

    public DenomTraceResponse() {
    }

    public DenomTraceResponse(DenomTrace denomTrace) {
        this.denomTrace = denomTrace;
    }

    public DenomTrace getDenomTrace() {
        return denomTrace;
    }

    public void setDenomTrace(DenomTrace denomTrace) {
        this.denomTrace = denomTrace;
    }
}
