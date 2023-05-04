package com.okbchain.msg.staking;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgDestroyValidator extends MsgBase {

    public MsgDestroyValidator() { setMsgType("okbchain/staking/MsgDestroyValidator"); }

    public Message produceMsg() {

        MsgDestroyValidatorValue value = new MsgDestroyValidatorValue();

        value.setDelegatorAddress(this.address);

        Message<MsgDestroyValidatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
