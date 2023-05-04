package com.okbchain.msg.staking;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgUnbindProxy extends MsgBase {

    public MsgUnbindProxy() { setMsgType("okbchain/staking/MsgUnbindProxy"); }

    public Message produce() {

        MsgUnbindProxyValue value = new MsgUnbindProxyValue();

        value.setDelegatorAddress(this.address);

        Message<MsgUnbindProxyValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
