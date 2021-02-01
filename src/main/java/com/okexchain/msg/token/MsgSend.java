package com.okexchain.msg.token;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.AddressUtil;

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
        amount.setAmount(Utils.NewDecString(amountDenom));
        amountList.add(amount);

        MsgSendValue value = new MsgSendValue();
        value.setFromAddress(this.address);
        // eth address
        if (!to.startsWith(EnvInstance.getEnv().GetMainPrefix())) {
            to = AddressUtil.convertAddressFromHexToBech32(to);
        }
        value.setToAddress(to);
        value.setAmount(amountList);

        Message<MsgSendValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
