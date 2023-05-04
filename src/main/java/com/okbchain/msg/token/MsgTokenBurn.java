package com.okbchain.msg.token;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

public class MsgTokenBurn extends MsgBase {

    public MsgTokenBurn() { setMsgType("okbchain/token/MsgBurn");}

    public Message produceTokenBurnMsg(String denom, String amountDenom, String owner) {

        Token amount = new Token();
        amount.setDenom(denom);
        amount.setAmount(Utils.NewDecString(amountDenom));

        MsgTokenBurnValue value = new MsgTokenBurnValue();
        value.setAmount(amount);
        value.setOwner(owner);

        Message<MsgTokenBurnValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }

}