package io.okchain.types;

import com.google.gson.annotations.SerializedName;
import io.cosmos.types.Fee;
import io.cosmos.types.Signature;
import io.cosmos.types.TransferMessage;

import java.util.List;

public class OKChainValue {
    @SerializedName("msg")
    private TransferMessage[] msgs;

    private Fee fee;

    private List<Signature> signatures;

    private String memo;

    public TransferMessage[] getMsgs() {
        return msgs;
    }

    public void setMsgs(TransferMessage[] msgs) {
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
