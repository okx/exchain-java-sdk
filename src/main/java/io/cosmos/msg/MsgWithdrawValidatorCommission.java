package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgWithdrawValidatorCommissionValue;

public class MsgWithdrawValidatorCommission extends MsgBase {

    public static void main(String[] args) {
        MsgWithdrawValidatorCommission msg = new MsgWithdrawValidatorCommission();
        msg.setMsgType("cosmos-sdk/MsgWithdrawValidatorCommission");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());


        Message messages = msg.produceMsg();

        msg.submit(messages,
                "6",
                "200000",
                "cosmos withdraw");
    }

    public Message produceMsg() {
        String validatorAddr = this.operAddress;
        MsgWithdrawValidatorCommissionValue value = new MsgWithdrawValidatorCommissionValue();
        value.setValidatorAddress(validatorAddr);

        Message<MsgWithdrawValidatorCommissionValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
