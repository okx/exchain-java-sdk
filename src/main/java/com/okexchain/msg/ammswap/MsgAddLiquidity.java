package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.time.Duration;
import java.time.Instant;

public class MsgAddLiquidity extends MsgBase {

    public MsgAddLiquidity() { setMsgType("okexchain/ammswap/MsgAddLiquidity"); }

    public Message produceMsg(String deadline, String minLiquidity, String maxBaseAmount, String baseDenom, String quoteAmount, String quoteDenom) {

        Token coinMaxBaseAmount = new Token();
        coinMaxBaseAmount.setDenom(baseDenom);
        coinMaxBaseAmount.setAmount(Utils.NewDecString(maxBaseAmount));

        Token coinQuoteAmount = new Token();
        coinQuoteAmount.setDenom(quoteDenom);
        coinQuoteAmount.setAmount(Utils.NewDecString(quoteAmount));

        MsgAddLiquidityValue value = new MsgAddLiquidityValue();

        long current = Instant.now().getEpochSecond() + Duration.parse(deadline).getSeconds();
        value.setDeadline(Long.toString(current));
        value.setSender(this.address);
        value.setMinLiquidity(Utils.NewDecString(minLiquidity));
        value.setMaxBaseAmount(coinMaxBaseAmount);
        value.setQuoteAmount(coinQuoteAmount);

        Message<MsgAddLiquidityValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
