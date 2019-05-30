package io.okchain.types;

import com.alibaba.fastjson.annotation.JSONField;

public class SignData {
    public SignData() {
    }

    public SignData(String accountNumber, String chainId, Fee fee, String memo, IMsg[] msgs, String sequence) {
        this.accountNumber = accountNumber;
        this.chainId = chainId;
        this.fee = fee;
        this.memo = memo;
        this.msgs = msgs;
        this.sequence = sequence;
    }


    @JSONField(name = "account_number")
    private String accountNumber;

    @JSONField(name = "chain_id")
    private String chainId;

    @JSONField(name = "fee")
    private Fee fee;

    @JSONField(name = "memo")
    private String memo;

    @JSONField(name = "msgs")
    private IMsg[] msgs;

    @JSONField(name = "sequence")
    private String sequence;

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

    public IMsg[] getMsgs() {
        return msgs;
    }

    public void setMsgs(IMsg[] msgs) {
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

}
