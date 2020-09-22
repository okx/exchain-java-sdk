package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgEditValidatorValue;
import io.cosmos.types.Description;

public class MsgEditValidator extends MsgBase {

    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgEditValidator msg = new MsgEditValidator();
        msg.setMsgType("cosmos-sdk/MsgEditValidator");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg();

        msg.submit(messages, "6", "200000", "");
    }

    public Message produceMsg() {

        Description d = new Description();
        d.setDetails("1");
        d.setIdentity("1");
        d.setMoniker("m1");
        d.setWebsite("1");

        MsgEditValidatorValue value = new MsgEditValidatorValue();

        System.out.println("this.operAddress:");
        System.out.println(this.operAddress);

        value.setAddress(this.operAddress);
        value.setCommissionRate("0.600000000000000000");
        value.setCommissionRate(null);
        value.setDescription(d);
        value.setMinSelfDelegation("1110");

        Message<MsgEditValidatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
