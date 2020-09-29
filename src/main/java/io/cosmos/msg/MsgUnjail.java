package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgUnjailValue;
import sun.lwawt.macosx.CSystemTray;

public class MsgUnjail  extends MsgBase {
    public MsgUnjail () {
        setMsgType("cosmos-sdk/MsgUnjail");
    }

    public static void main(String[] args) {
        EnvInstance.setEnv("okl");
        MsgUnjail msg = new MsgUnjail();
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
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

