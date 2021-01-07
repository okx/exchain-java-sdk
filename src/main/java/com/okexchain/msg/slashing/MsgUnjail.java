package com.okexchain.msg.slashing;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgUnjail  extends MsgBase {
    public MsgUnjail () {
        setMsgType("cosmos-sdk/MsgUnjail");
    }

    public static void main(String[] args) {
        EnvInstance.setEnv("okl");
        MsgUnjail msg = new MsgUnjail();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        Message messages = msg.produceMsg("okexchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frshchly8");
        msg.submit(messages, "100000.00000000", "200000", "");
    }

    public Message produceMsg(String operAddress) {
        MsgUnjailValue value = new MsgUnjailValue();
        value.setAddress(operAddress);

        Message<MsgUnjailValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}

