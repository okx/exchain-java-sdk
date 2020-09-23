package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgSendValue;
import io.cosmos.types.Token;

import java.util.ArrayList;
import java.util.List;

public class MsgSend extends MsgBase {

    public MsgSend() {
        setMsgType("okexchain/token/MsgTransfer");
    }

    public static void main(String[] args) {
        MsgSend msg = new MsgSend();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceSendMsg(
                "okt",
                "6.00000000",
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9");

        // okexchaincli tx send okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9 okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9 6okt --from captain -y -b block --fees 0.01okt
        msg.submit(messages, "0.01000000", "200000", "okexchain transfer!");
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
