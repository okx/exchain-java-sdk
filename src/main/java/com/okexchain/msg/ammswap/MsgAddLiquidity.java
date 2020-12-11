package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgAddLiquidity extends MsgBase {

    public MsgAddLiquidity() { setMsgType("okexchain/ammswap/MsgAddLiquidity"); }

    public Message produceMsg(int deadline, String minLiquidity, String amountMaxBaseAmount, String denomMaxBaseAmount, String amountQuoteAmount, String denomQuoteAmount) {

        Token coinMaxBaseAmount = new Token();
        coinMaxBaseAmount.setDenom(amountMaxBaseAmount);
        coinMaxBaseAmount.setAmount(Utils.NewDecString(denomMaxBaseAmount));

        Token coinQuoteAmount = new Token();
        coinQuoteAmount.setDenom(amountQuoteAmount);
        coinQuoteAmount.setAmount(Utils.NewDecString(denomQuoteAmount));

        MsgAddLiquidityValue value = new MsgAddLiquidityValue();

        value.setDeadline(deadline);
        value.setSender(this.address);
        value.setMinLiquidity(minLiquidity);
        value.setMaxBaseAmount(coinMaxBaseAmount);
        value.setQuoteAmount(coinQuoteAmount);

        Message<MsgAddLiquidityValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
