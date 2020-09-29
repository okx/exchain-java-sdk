package io.okexchain.types;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class StdTransaction {
    @JSONField(name = "msg")
    private IMsg[] msgs;

    private Fee fee;

    private List<Signature> signatures;

    private String memo;

    public StdTransaction(IMsg[] msgs, Fee fee, List<Signature> signatures, String memo) {
        this.msgs = msgs;
        this.fee = fee;
        this.signatures = signatures;
        this.memo = memo;
    }

    public IMsg[] getMsgs() {
        return msgs;
    }

    public void setMsgs(IMsg[] msgs) {
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

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures;
    }
}
