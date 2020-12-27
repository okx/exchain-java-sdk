package com.okexchain.msg.tx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.google.protobuf.UInt32Value;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Response {
    @JsonProperty("height")
    @SerializedName("height")
    long height;

    @JsonProperty("txhash")
    @SerializedName("txhash")
    String txhash;

    @JsonProperty("codespace")
    @SerializedName("codespace")
    String codespace;

    @JsonProperty("code")
    @SerializedName("code")
    int code;

    @JsonProperty("data")
    @SerializedName("data")
    String data;

    @JsonProperty("raw_log")
    @SerializedName("raw_log")
    String rawLog;

    @JsonProperty("info")
    @SerializedName("info")
    String info;

    @JsonProperty("gas_wanted")
    @SerializedName("gas_wanted")
    long gas;

    @JsonProperty("gas_used")
    @SerializedName("gas_used")
    long gasUsed;

    public long getHeight() {
        return height;
    }

    public String getTxhash() {
        return txhash;
    }

    public String getCodespace() {
        return codespace;
    }

    public int getCode() {
        return code;
    }

    public String getData() {
        return data;
    }

    public String getRawLog() {
        return rawLog;
    }

    public String getInfo() {
        return info;
    }

    public long getGas() {
        return gas;
    }

    public long getGasUsed() {
        return gasUsed;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public void setCodespace(String codespace) {
        this.codespace = codespace;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setRawLog(String rawLog) {
        this.rawLog = rawLog;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setGas(long gas) {
        this.gas = gas;
    }

    public void setGasUsed(long gasUsed) {
        this.gasUsed = gasUsed;
    }
}

