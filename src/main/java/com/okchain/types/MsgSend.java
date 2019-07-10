package com.okchain.types;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class MsgSend implements IMsg {

    private List<Token> amount;

    @JSONField(name = "from_address")
    private String fromAddress;

    @JSONField(name = "to_address")
    private String toAddress;



    public MsgSend(String fromAddress, String toAddress, List<Token> amount) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
    }

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    public List<Token> getAmount() {
        return amount;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

}
