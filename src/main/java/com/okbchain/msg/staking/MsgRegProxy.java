package com.okbchain.msg.staking;

import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgRegProxy extends MsgBase {

    public MsgRegProxy() {
        setMsgType("okbchain/staking/MsgRegProxy");
    }

    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgRegProxy msg = new MsgRegProxy();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceMsg("okbchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9", true);

        msg.submit(messages, "6.00000000", "200000", "");
    }

    public Message produceMsg(String proxyAddress, boolean reg) {
        MsgRegProxyValue value = new MsgRegProxyValue();

        System.out.println("this.operAddress:");
        System.out.println(this.operAddress);

        value.setProxyAddress(proxyAddress);
        value.setReg(reg);

        Message<MsgRegProxyValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
