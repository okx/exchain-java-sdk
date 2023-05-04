package com.okbchain.msg.token;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgSend extends MsgBase {

    public MsgSend() {
        setMsgType("okbchain/token/MsgTransfer");
    }

    public static void main(String[] args) {
        EnvInstance.getEnvLocalNet();
        MsgSend msg = new MsgSend();


        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceSendMsg(
                "okb",
                "6.00000000",
                "ex1h0j8x0v9hs4eq6ppgamemfyu4vuvp2sl0q9p3v");

        // okbchaincli tx send okbchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9 okbchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9 6okt --from captain -y -b block --fees 0.01okt
        JSONObject obj = msg.submit(messages, "0.01000000", "200000", "okbchain transfer!");
        System.out.println(obj);
        try {
            System.out.println(msg.isTxSucceed(obj));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Message produceSendMsg(String denom, String amountDenom, String to) {

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom(denom);
        amount.setAmount(Utils.NewDecString(amountDenom));
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
