package com.okexchain.msg.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Attribute {
    @JsonProperty("key")
    @SerializedName("key")
    private String key;

    @JsonProperty("value")
    @SerializedName("value")
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
