package com.okexchain.msg.ibc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.TimeoutHeight;
import com.okexchain.msg.common.Token;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferMsgValue {

    @JsonProperty("source_port")
    @SerializedName("source_port")
    private String sourcePort;

    @JsonProperty("source_channel")
    @SerializedName("source_channel")
    private String sourceChannel;

    @JsonProperty("token")
    @SerializedName("token")
    private Token token;

    @JsonProperty("sender")
    @SerializedName("sender")
    private String sender;

    @JsonProperty("receiver")
    @SerializedName("receiver")
    private String receiver;

    @JsonProperty("timeout_height")
    @SerializedName("timeout_height")
    private TimeoutHeight timeoutHeight;


    public TransferMsgValue() {
    }

    public TransferMsgValue(String sourcePort, String sourceChannel, Token token, String sender, String receiver, TimeoutHeight timeoutHeight) {
        this.sourcePort = sourcePort;
        this.sourceChannel = sourceChannel;
        this.token = token;
        this.sender = sender;
        this.receiver = receiver;
        this.timeoutHeight = timeoutHeight;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getSourceChannel() {
        return sourceChannel;
    }

    public void setSourceChannel(String sourceChannel) {
        this.sourceChannel = sourceChannel;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public TimeoutHeight getTimeoutHeight() {
        return timeoutHeight;
    }

    public void setTimeoutHeight(TimeoutHeight timeoutHeight) {
        this.timeoutHeight = timeoutHeight;
    }

    @Override
    public String toString() {
        return "TransferMsgValue{" +
                "sourcePort='" + sourcePort + '\'' +
                ", sourceChannel='" + sourceChannel + '\'' +
                ", token=" + token +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", timeoutHeight=" + timeoutHeight +
                '}';
    }
}
