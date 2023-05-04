
package com.okbchain.msg.staking;

import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgBindProxy extends MsgBase {

    public MsgBindProxy() {
        setMsgType("okbchain/staking/MsgBindProxy");
    }
    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgBindProxy msg = new MsgBindProxy();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceMsg("okbchain1fqc7fw5ka5md4jyrnj53n2m82f5jewz9vrvnan", "okbchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9");

        msg.submit(messages, "6.00000000", "200000", "");
    }

    public Message produceMsg(String delegator_address, String proxy_address) {
        MsgBindProxyValue value = new MsgBindProxyValue();

        System.out.println("this.operAddress:");
        System.out.println(this.operAddress);

        value.setDelAddress(delegator_address);
        value.setProxyAddress(proxy_address);

        Message<MsgBindProxyValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
