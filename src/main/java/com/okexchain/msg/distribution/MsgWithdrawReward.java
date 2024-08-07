package com.okexchain.msg.distribution;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgWithdrawReward extends MsgBase {

    public MsgWithdrawReward() {
        setMsgType("okexchain/distribution/MsgWithdrawReward");
    }

    public static void main(String[] args) {
        MsgWithdrawReward msg = new MsgWithdrawReward();

        msg.initMnemonic("");

        Message messages = msg.produceWithdrawRewardMsg(
                "okexchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frshchly8");

        msg.submit(messages, "0.01000000", "200000", "okexchain withdraw reward!");
    }

    public Message produceWithdrawRewardMsg(String withdrawAddress) {

        MsgWithdrawRewardValue value = new MsgWithdrawRewardValue();
        value.setValidatorAddress(withdrawAddress);

        Message<MsgWithdrawRewardValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
