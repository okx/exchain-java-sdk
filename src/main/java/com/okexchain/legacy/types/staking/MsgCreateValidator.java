package com.okexchain.legacy.types.staking;

import com.alibaba.fastjson.annotation.JSONField;
import com.okexchain.legacy.types.IMsg;
import com.okexchain.legacy.types.Token;

public class MsgCreateValidator implements IMsg {
    private CommissionRates commission;

    @JSONField(name = "delegator_address")
    private String delegatorAddress;

    private Description description;

    @JSONField(name = "min_self_delegation")
    private Token minSelfDelegation;

    @JSONField(name = "pubkey")
    private String pubKey;

    @JSONField(name = "validator_address")
    private String validatorAddress;


    public MsgCreateValidator(Description description, CommissionRates commission, Token minSelfDelegation,
                              String delegatorAddress, String validatorAddress, String pubKey) {
        this.description = description;
        this.commission = commission;
        this.minSelfDelegation = minSelfDelegation;
        this.delegatorAddress = delegatorAddress;
        this.validatorAddress = validatorAddress;
        this.pubKey = pubKey;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public CommissionRates getCommission() {
        return commission;
    }

    public void setCommission(CommissionRates commission) {
        this.commission = commission;
    }

    public Token getMinSelfDelegation() {
        return minSelfDelegation;
    }

    public void setMinSelfDelegation(Token minSelfDelegation) {
        this.minSelfDelegation = minSelfDelegation;
    }

    public String getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(String delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public String getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(String validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
