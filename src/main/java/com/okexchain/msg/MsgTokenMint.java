package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgTokenMintValue;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgTokenMint extends MsgBase {

    public MsgTokenMint() { setMsgType("okexchain/token/MsgMint");}

    public Message produceTokenMintMsg(String denom, String amountDenom, String owner) {

        Token amount = new Token();
        amount.setDenom(denom);
        amount.setAmount(Utils.NewDecString(amountDenom));

        MsgTokenMintValue value = new MsgTokenMintValue();
        value.setAmount(amount);
        value.setOwner(owner);

        Message<MsgTokenMintValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }

}