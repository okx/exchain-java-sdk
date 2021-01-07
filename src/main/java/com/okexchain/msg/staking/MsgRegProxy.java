package com.okexchain.msg.staking;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgRegProxy extends MsgBase {

    public MsgRegProxy() {
        setMsgType("okexchain/staking/MsgRegProxy");
    }

    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgRegProxy msg = new MsgRegProxy();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceMsg("okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9", true);

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
