package io.okexchain.crypto.io.cosmos.types;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-21 10:26
 **/
public class CosmosValue {

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
