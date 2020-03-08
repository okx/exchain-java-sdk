package com.okchain.types.staking;

import com.alibaba.fastjson.annotation.JSONField;
import com.okchain.types.IMsg;
import com.okchain.types.Token;

public class MsgEditValidator implements IMsg {
    @JSONField(name = "Description")
    private Description description;

    @JSONField(name = "address")
    private String validatorAddress;

    @JSONField(name = "min_self_delegation")
    private String minSelfDelegation;

    public MsgEditValidator(String validatorAddress, Description description, String minSelfDelegation) {
        this.validatorAddress = validatorAddress;
        this.description = description;
        this.minSelfDelegation = minSelfDelegation;
    }

    public String getValidatorAddress() {
        return validatorAddress;
    }

    public void setValidatorAddress(String validatorAddress) {
        this.validatorAddress = validatorAddress;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getMinSelfDelegation() {
        return minSelfDelegation;
    }

    public void setMinSelfDelegation(String minSelfDelegation) {
        this.minSelfDelegation = minSelfDelegation;
    }
}
