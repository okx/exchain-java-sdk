package io.okchain.types;

import com.google.gson.annotations.SerializedName;
import io.okchain.types.Fee;
import io.okchain.types.Signature;


import java.util.List;

public class StdTransaction {
    public StdTransaction() {
    }

    public StdTransaction(IMsg[] msgs, Fee fee, List<Signature> signatures, String memo) {
        this.msgs = msgs;
        this.fee = fee;
        this.signatures = signatures;
        this.memo = memo;
    }

    @SerializedName("msg")
    private IMsg[] msgs;

    private Fee fee;

    private List<Signature> signatures;

    private String memo;

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
