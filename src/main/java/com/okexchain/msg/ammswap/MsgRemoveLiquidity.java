package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgRemoveLiquidity extends MsgBase {

    public MsgRemoveLiquidity() { setMsgType("okexchain/ammswap/MsgRemoveLiquidity"); }

    public Message produce(int deadline, String liquidity, String amountMinBaseAmount, String denomMinBaseAmount, String amountMinQuoteAmount, String denomMinQuoteAmount){

        Token coinMinBaseAmount = new Token();
        coinMinBaseAmount.setDenom(amountMinBaseAmount);
        coinMinBaseAmount.setAmount(Utils.NewDecString(denomMinBaseAmount));

        Token coinMinQuoteAmount = new Token();
        coinMinQuoteAmount.setDenom(amountMinQuoteAmount);
        coinMinQuoteAmount.setAmount(Utils.NewDecString(denomMinQuoteAmount));

        MsgRemoveLiquidityValue value = new MsgRemoveLiquidityValue();

        value.setDeadline(deadline);
        value.setSender(this.address);
        value.setMinBaseAmount(coinMinBaseAmount);
        value.setMinQuoteAmount(coinMinQuoteAmount);
        value.setLiquidity(liquidity);

        Message<MsgRemoveLiquidityValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
