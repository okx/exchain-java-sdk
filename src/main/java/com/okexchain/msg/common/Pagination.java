package com.okexchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

/**
 * response Pagination
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Pagination {

    @JsonProperty("total")
    @SerializedName("total")
    private int total;


    @JsonProperty("next_key")
    @SerializedName("next_key")
    private String nextKey;

    public Pagination() {
    }

    public Pagination(int total, String nextKey) {
        this.total = total;
        this.nextKey = nextKey;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "total=" + total +
                ", nextKey='" + nextKey + '\'' +
                '}';
    }
}
