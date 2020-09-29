package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgEditValidatorValue;
import io.cosmos.types.Description;

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
