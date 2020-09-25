package io.cosmos.msg;

import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgToeknBurnValue;
import io.cosmos.types.Token;

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