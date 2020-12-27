package com.okexchain.msg.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StringEvent<T> {
    @JsonProperty("type")
    @SerializedName("type")
    private String type;

    @JsonProperty("attributes")
    @SerializedName("attributes")
    private List<T> attributes;

    public String getType() {
        return type;
    }

    public List<T> getAttributes() {
        return attributes;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAttributes(List<T> attributes) {
        this.attributes = attributes;
    }
}
