package com.okexchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TimeoutHeight {


    @JsonProperty("revision_number")
    @SerializedName("revision_number")
    private String revisionNumber;

    @JsonProperty("revision_height")
    @SerializedName("revision_height")
    private String revisionHeight;

    public TimeoutHeight(String revisionNumber, String revisionHeight) {
        this.revisionNumber = revisionNumber;
        this.revisionHeight = revisionHeight;
    }

    public String getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(String revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public String getRevisionHeight() {
        return revisionHeight;
    }

    public void setRevisionHeight(String revisionHeight) {
        this.revisionHeight = revisionHeight;
    }

    @Override
    public String toString() {
        return "TimeoutHeight{" +
                "revisionNumber='" + revisionNumber + '\'' +
                ", revisionHeight='" + revisionHeight + '\'' +
                '}';
    }
}
