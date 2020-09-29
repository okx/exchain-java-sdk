
package io.cosmos.msg;

import com.sun.org.apache.xpath.internal.objects.XString;
import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgAddSharesValue;
import io.cosmos.msg.utils.type.MsgBindProxyValue;
import io.cosmos.msg.utils.type.MsgRegProxyValue;

public class MsgBindProxy extends MsgBase {

    public MsgBindProxy() {
        setMsgType("okexchain/staking/MsgBindProxy");
    }
    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgBindProxy msg = new MsgBindProxy();
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg("okexchain1fqc7fw5ka5md4jyrnj53n2m82f5jewz9vrvnan", "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9");

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
