package com.okexchain.msg.token;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgTokenBurn extends MsgBase {

    public MsgTokenBurn() { setMsgType("okexchain/token/MsgBurn");}

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