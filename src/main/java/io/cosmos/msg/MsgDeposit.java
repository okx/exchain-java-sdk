package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgEditValidatorValue;
import io.cosmos.types.Description;

public class MsgDeposit  extends MsgBase {
    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgDeposit msg = new MsgDeposit();
        msg.setMsgType("cosmos-sdk/MsgEditValidator");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

    }

}
