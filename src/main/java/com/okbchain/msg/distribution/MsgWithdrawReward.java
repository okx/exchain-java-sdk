package com.okbchain.msg.distribution;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgWithdrawReward extends MsgBase {

    public MsgWithdrawReward() {
        setMsgType("okbchain/distribution/MsgWithdrawReward");
    }

    public static void main(String[] args) {
        MsgWithdrawReward msg = new MsgWithdrawReward();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceWithdrawRewardMsg(
                "okbchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frshchly8");

        msg.submit(messages, "0.01000000", "200000", "okbchain withdraw reward!");
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
