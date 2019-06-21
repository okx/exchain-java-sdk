package com.okchain.types;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class MsgMultiSend implements IMsg {
    @JSONField(name = "From")
    private String from;

    @JSONField(name = "Transfers")
    private List<TransferUnit> transfers;

    public MsgMultiSend(String from, List<TransferUnit> transfers) {
        this.from = from;
        this.transfers = transfers;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<TransferUnit> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferUnit> transfers) {
        this.transfers = transfers;
    }
}
