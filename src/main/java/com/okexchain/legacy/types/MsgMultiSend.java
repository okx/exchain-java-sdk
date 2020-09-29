package com.okexchain.legacy.types;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class MsgMultiSend implements IMsg {
    @JSONField(name = "from")
    private String from;

    @JSONField(name = "transfers")
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
