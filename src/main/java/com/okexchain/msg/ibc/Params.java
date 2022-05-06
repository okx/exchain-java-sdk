package com.okexchain.msg.ibc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Params {

    @JsonProperty("send_enabled")
    @SerializedName("send_enabled")
    private boolean sendEnabled;

    @JsonProperty("receive_enabled")
    @SerializedName("receive_enabled")
    private boolean receiveEnabled;

    public Params() {
    }

    public Params(boolean sendEnabled, boolean receiveEnabled) {
        this.sendEnabled = sendEnabled;
        this.receiveEnabled = receiveEnabled;
    }

    public boolean isSendEnabled() {
        return sendEnabled;
    }

    public void setSendEnabled(boolean sendEnabled) {
        this.sendEnabled = sendEnabled;
    }

    public boolean isReceiveEnabled() {
        return receiveEnabled;
    }

    public void setReceiveEnabled(boolean receiveEnabled) {
        this.receiveEnabled = receiveEnabled;
    }

    @Override
    public String toString() {
        return "Params{" +
                "sendEnabled=" + sendEnabled +
                ", receiveEnabled=" + receiveEnabled +
                '}';
    }
}
