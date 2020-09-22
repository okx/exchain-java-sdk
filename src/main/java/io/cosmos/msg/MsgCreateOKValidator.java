package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgCreateOKValidatorValue;
import io.cosmos.types.CommissionMsg;
import io.cosmos.types.Description;
import io.cosmos.types.Token;


public class MsgCreateOKValidator extends MsgBase {

    public static void main(String[] args) {
        EnvInstance.setEnv("ok");
        MsgCreateOKValidator msg = new MsgCreateOKValidator();
        msg.setMsgType("cosmos-sdk/MsgCreateValidator");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());
        Message messages = msg.produceMsg();
        msg.submit(messages, "6", "200000", "");
    }

    public Message produceMsg() {
        MsgCreateOKValidatorValue value = new MsgCreateOKValidatorValue();

        value.setDelegatorAddress(this.address);
        value.setValidatorAddress(this.operAddress);
        value.setPubKey("okchainvalconspub1zcjduepqwfr8lelpqerf8xyc63vqtje0wvhd68h7uce6ludygc28uj5hc9ushev2kp");

        Description d = new Description();
        d.setDetails("1");
        d.setIdentity("2");
        d.setMoniker("3");
        d.setWebsite("4");

        CommissionMsg c = new CommissionMsg();
        c.setMaxChangeRate("0.05000000");
        c.setMaxRate("0.05000000");
        c.setRate("0.05000000");

        Token t = new Token();
        t.setAmount("68.00000000");
        t.setDenom(EnvInstance.getEnv().GetDenom());

        value.setCommission(c);
        value.setDescription(d);
        value.setMinSelfDelegation(t);


        Message<MsgCreateOKValidatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
