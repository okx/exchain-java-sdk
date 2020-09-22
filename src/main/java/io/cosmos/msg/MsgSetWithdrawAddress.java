package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgSetWithdrawAddrValue;


public class MsgSetWithdrawAddress extends MsgBase {

    public static void main(String[] args) {
        MsgSetWithdrawAddress msg = new MsgSetWithdrawAddress();
        msg.setMsgType("cosmos-sdk/MsgModifyWithdrawAddress");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg();

        msg.submit(messages,
                "6",
                "200000",
                "cosmos set withdrawAddr");
    }

    public Message produceMsg() {
        String withdrawAddr = this.address;
        MsgSetWithdrawAddrValue value = new MsgSetWithdrawAddrValue();
        value.setWithdrawAddress(withdrawAddr);
        value.setDelegatorAddress(this.address);

        Message<MsgSetWithdrawAddrValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
