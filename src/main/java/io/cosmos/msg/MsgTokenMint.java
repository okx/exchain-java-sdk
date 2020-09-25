package io.cosmos.msg;

import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgTokenMintValue;
import io.cosmos.types.Token;

public class MsgTokenMint extends MsgBase {

    public MsgTokenMint() { setMsgType("okexchain/token/MsgMint");}

    public Message produceTokenMintMsg(String denom, String amountDenom, String owner) {

        Token amount = new Token();
        amount.setDenom(denom);
        amount.setAmount(amountDenom);

        MsgTokenMintValue value = new MsgTokenMintValue();
        value.setAmount(amount);
        value.setOwner(owner);

        Message<MsgTokenMintValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }

}