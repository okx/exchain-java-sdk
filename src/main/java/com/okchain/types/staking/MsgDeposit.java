package com.okchain.types.staking;

import com.okchain.types.IMsg;
import com.alibaba.fastjson.annotation.JSONField;
import com.okchain.types.Token;

public class MsgDeposit implements IMsg {
    @JSONField(name = "delegator_address")
    private String delegatorAddress;

    @JSONField(name = "quantity")
    private Token amount;


    public MsgDeposit(String delegatorAddress, Token amount) {
        this.delegatorAddress = delegatorAddress;
        this.amount = amount;
    }

    public String getDelegatorAddress() { return delegatorAddress; }

    public void setDelegatorAddress(String delegatorAddress) { this.delegatorAddress = delegatorAddress; }

    public Token getAmount() { return amount; }

    public void setAmount(Token amount) { this.amount = amount; }
}
