package com.okexchain.msg.staking;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Description;

public class MsgEditValidator extends MsgBase {
    public MsgEditValidator() {
        setMsgType("okexchain/staking/MsgEditValidator");
    }
    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgEditValidator msg = new MsgEditValidator();
        msg.setMsgType("okexchain/staking/MsgEditValidator");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg("1","1","1","1", "okexchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frshchly8");

        msg.submit(messages, "6.00000000", "200000", "");
    }

    public Message produceMsg(String details, String identity, String moniker, String website, String operAddress) {

        Description d = new Description();
        d.setDetails(details);
        d.setIdentity(identity);
        d.setMoniker(moniker);
        d.setWebsite(website);

        MsgEditValidatorValue value = new MsgEditValidatorValue();

        value.setAddress(operAddress);

        value.setDescription(d);

        Message<MsgEditValidatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
