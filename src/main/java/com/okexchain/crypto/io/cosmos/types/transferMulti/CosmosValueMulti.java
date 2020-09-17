package com.okexchain.crypto.io.cosmos.types.transferMulti;

import com.google.gson.annotations.SerializedName;
import com.okexchain.crypto.io.cosmos.types.Fee;
import com.okexchain.crypto.io.cosmos.types.Signature;

import java.util.List;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-25 13:54
 **/
public class CosmosValueMulti {

    @SerializedName("msg")
    private TransferMessageMulti[] msgs;

    private Fee fee;

    private List<Signature> signatures;

    private String memo;

    public TransferMessageMulti[] getMsgs() {
        return msgs;
    }

    public void setMsgs(TransferMessageMulti[] msgs) {
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
