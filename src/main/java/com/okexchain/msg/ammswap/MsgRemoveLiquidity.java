package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.time.Duration;
import java.time.Instant;

public class MsgRemoveLiquidity extends MsgBase {

    public MsgRemoveLiquidity() { setMsgType("okexchain/ammswap/MsgRemoveLiquidity"); }

    public Message produce(String deadline, String liquidity, String minBaseAmount, String BaseDenom, String minQuoteAmount, String quoteDenom){

        Token coinMinBaseAmount = new Token();
        coinMinBaseAmount.setDenom(BaseDenom);
        coinMinBaseAmount.setAmount(Utils.NewDecString(minBaseAmount));

        Token coinMinQuoteAmount = new Token();
        coinMinQuoteAmount.setDenom(quoteDenom);
        coinMinQuoteAmount.setAmount(Utils.NewDecString(minQuoteAmount));

        MsgRemoveLiquidityValue value = new MsgRemoveLiquidityValue();

        long current = Instant.now().getEpochSecond() + Duration.parse(deadline).getSeconds();
        value.setDeadline(Long.toString(current));
        value.setSender(this.address);
        value.setMinBaseAmount(coinMinBaseAmount);
        value.setMinQuoteAmount(coinMinQuoteAmount);
        value.setLiquidity(Utils.NewDecString(liquidity));

        Message<MsgRemoveLiquidityValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
