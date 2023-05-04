package com.okbchain.msg.token;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

public class MsgTokenMint extends MsgBase {

    public MsgTokenMint() { setMsgType("okbchain/token/MsgMint");}

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