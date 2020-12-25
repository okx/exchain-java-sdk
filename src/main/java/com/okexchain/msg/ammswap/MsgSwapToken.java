package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgSwapToken extends MsgBase {

    public MsgSwapToken() { setMsgType("okexchain/ammswap/MsgSwapToken"); }

    public Message produceMsg(String deadline, String recipient, String amountMinBoughtToken, String denomMinBoughtToken, String amountSoldToken, String denomSoldToken) {

        Token coinMinBoughtToken = new Token();
        coinMinBoughtToken.setDenom(amountMinBoughtToken);
        coinMinBoughtToken.setAmount(Utils.NewDecString(denomMinBoughtToken));

        Token coinSoldToken = new Token();
        coinSoldToken.setDenom(amountSoldToken);
        coinSoldToken.setAmount(Utils.NewDecString(denomSoldToken));

        MsgSwapTokenValue value = new MsgSwapTokenValue();

        value.setDeadline(Utils.NewDecString(deadline));
        value.setSender(this.address);
        value.setRecipient(recipient);
        value.setMinBoughtTokenAmount(coinMinBoughtToken);
        value.setSoldTokenAmount(coinSoldToken);

        Message<MsgSwapTokenValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
