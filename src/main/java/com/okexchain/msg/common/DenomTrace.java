package com.okexchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class DenomTrace {

    @JsonProperty("path")
    @SerializedName("path")
    private String path;

    @JsonProperty("base_denom")
    @SerializedName("base_denom")
    private String baseDenom;


    public DenomTrace() {
    }

    public DenomTrace(String path, String baseDenom) {
        this.path = path;
        this.baseDenom = baseDenom;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBaseDenom() {
        return baseDenom;
    }

    public void setBaseDenom(String baseDenom) {
        this.baseDenom = baseDenom;
    }

    @Override
    public String toString() {
        return "DenomTrace{" +
                "path='" + path + '\'' +
                ", baseDenom='" + baseDenom + '\'' +
                '}';
    }
}
