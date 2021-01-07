package com.okexchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.utils.Utils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Data2Sign {

    @JsonProperty("account_number")
    @SerializedName("account_number")
    private String accountNumber;

    @JsonProperty("chain_id")
    @SerializedName("chain_id")
    private String chainId;

    @JsonProperty("fee")
    @SerializedName("fee")
    private Fee fee;

    @JsonProperty("memo")
    @SerializedName("memo")
    private String memo;

    @JsonProperty("msgs")
    @SerializedName("msgs")
    private Message[] msgs;

    @JsonProperty("sequence")
    @SerializedName("sequence")
    private String sequence;

    public Data2Sign(String accountNumber, String chainId, Fee fee, String memo, Message[] msgs, String sequence) {
        this.accountNumber = accountNumber;
        this.chainId = chainId;
        this.fee = fee;
        this.memo = memo;
        this.msgs = msgs;
        this.sequence = sequence;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Message[] getMsgs() {
        return msgs;
    }

    public void setMsgs(Message[] msgs) {
        this.msgs = msgs;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public void setData(Fee fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("account_number", accountNumber)
                .append("chain_id", chainId)
                .append("fee", fee)
                .append("memo", memo)
                .append("msgs", msgs)
                .append("sequence", sequence)
                .toString();
    }

    public String toJson() {
        return Utils.serializer.toJson(this);
    }

    public static Data2Sign fromJson(String json) {
        return Utils.serializer.fromJson(json, Data2Sign.class);
    }
}
