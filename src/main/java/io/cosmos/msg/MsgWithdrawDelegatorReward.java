package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgWithdrawDelegatorRewardValue;

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
