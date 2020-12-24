package com.okexchain.msg.staking;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgDestroyValidator extends MsgBase {

    public MsgDestroyValidator() { setMsgType("okexchain/staking/MsgDestroyValidator"); }

    public Message produceMsg() {

        MsgDestroyValidatorValue value = new MsgDestroyValidatorValue();

        value.setDelegatorAddress(this.address);

        Message<MsgDestroyValidatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
