package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.time.Duration;
import java.time.Instant;

public class MsgSwapToken extends MsgBase {

    public MsgSwapToken() { setMsgType("okexchain/ammswap/MsgSwapToken"); }

    public Message produceMsg(String deadline, String recipient, String minBoughtAmount, String boughtDenom, String soldAmount, String soldDenom) {

        Token coinMinBoughtToken = new Token();
        coinMinBoughtToken.setDenom(boughtDenom);
        coinMinBoughtToken.setAmount(Utils.NewDecString(minBoughtAmount));

        Token coinSoldToken = new Token();
        coinSoldToken.setDenom(soldDenom);
        coinSoldToken.setAmount(Utils.NewDecString(soldAmount));

        MsgSwapTokenValue value = new MsgSwapTokenValue();

        long current = Instant.now().getEpochSecond() + Duration.parse(deadline).getSeconds();
        value.setDeadline(Long.toString(current));
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
