package com.okexchain.msg;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgWithdrawDelegatorRewardValue;

public class MsgWithdrawDelegatorReward extends MsgBase {

    public static void main(String[] args) {
        MsgWithdrawDelegatorReward msg = new MsgWithdrawDelegatorReward();
        msg.setMsgType("cosmos-sdk/MsgWithdrawDelegationReward");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg();

        msg.submit(messages,
                "6",
                "200000",
                "cosmos set withdrawAddr");
    }

    public Message produceMsg() {
        String validatorAddr = this.operAddress;
        MsgWithdrawDelegatorRewardValue value = new MsgWithdrawDelegatorRewardValue();
        value.setValidatorAddress(validatorAddr);
        value.setDelegatorAddress(this.address);

        Message<MsgWithdrawDelegatorRewardValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
