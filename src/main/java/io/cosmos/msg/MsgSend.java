package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgSendValue;
import io.cosmos.types.Token;

import java.util.ArrayList;
import java.util.List;

public class MsgSend extends MsgBase {

    public static void main(String[] args) {
        EnvInstance.setEnv("okl");
        MsgSend msg = new MsgSend();

        msg.setMsgType("cosmos-sdk/MsgSend");

        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceSendMsg(
                EnvInstance.getEnv().GetDenom(),
                EnvInstance.getEnv().GetTransferAmount(),
                EnvInstance.getEnv().GetNode1Addr());

        msg.submit(messages, "6", "200000", "cosmos transfer!");
    }

    public Message produceSendMsg(String denom, String amountDenom, String to) {

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom(denom);
        amount.setAmount(amountDenom);
        amountList.add(amount);

        MsgSendValue value = new MsgSendValue();
        value.setFromAddress(this.address);
        value.setToAddress(to);
        value.setAmount(amountList);

        Message<MsgSendValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
