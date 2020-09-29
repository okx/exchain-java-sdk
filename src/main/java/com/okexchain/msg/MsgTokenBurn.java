package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgToeknBurnValue;
import com.okexchain.msg.common.Token;

public class MsgTokenBurn extends MsgBase {

    public MsgTokenBurn() { setMsgType("okexchain/token/MsgBurn");}

    public Message produceTokenBurnMsg(String denom, String amountDenom, String owner) {

        Token amount = new Token();
        amount.setDenom(denom);
        amount.setAmount(amountDenom);

        MsgToeknBurnValue value = new MsgToeknBurnValue();
        value.setAmount(amount);
        value.setOwner(owner);

        Message<MsgToeknBurnValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }

}