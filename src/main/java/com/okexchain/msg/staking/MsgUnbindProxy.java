package com.okexchain.msg.staking;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgUnbindProxy extends MsgBase {

    public MsgUnbindProxy() { setMsgType("okexchain/staking/MsgUnbindProxy"); }

    public Message produce() {

        MsgUnbindProxyValue value = new MsgUnbindProxyValue();

        value.setDelegatorAddress(this.address);

        Message<MsgUnbindProxyValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
