package com.okexchain.msg.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TxValue {

    @SerializedName("msg")
    private Message[] msgs;

    private Fee fee;

    private List<Signature> signatures;

    private String memo;

//    private  String type;

    public Message[] getMsgs() {
        return msgs;
    }

    public void setMsgs(Message[] msgs) {
        this.msgs = msgs;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public Fee getFee() {
        return fee;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

//    public String getType() {
//        return type;
//    }

//    public void setType(String type) {
//        this.type = type;
//    }

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }
}
