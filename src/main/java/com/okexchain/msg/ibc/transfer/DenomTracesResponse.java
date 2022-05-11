package com.okexchain.msg.ibc.transfer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.DenomTrace;
import com.okexchain.msg.common.Pagination;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class DenomTracesResponse{

    @JsonProperty("denom_traces")
    @SerializedName("denom_traces")
    private List<DenomTrace> denomTraces;


    @JsonProperty("pagination")
    @SerializedName("pagination")
    private Pagination pagination;

    public List<DenomTrace> getDenomTraces() {
        return denomTraces;
    }

    public void setDenomTraces(List<DenomTrace> denomTraces) {
        this.denomTraces = denomTraces;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "DenomTracesResponse{" +
                "denomTraces=" + denomTraces +
                ", pagination=" + pagination.toString() +
                '}';
    }
}
