package com.okexchain.msg.ammswap;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.text.SimpleDateFormat;

public class MsgAddLiquidity extends MsgBase {

    public MsgAddLiquidity() { setMsgType("okexchain/ammswap/MsgAddLiquidity"); }

    public Message produceMsg(String deadline, String minLiquidity, String amountMaxBaseAmount, String denomMaxBaseAmount, String amountQuoteAmount, String denomQuoteAmount) {

        Token coinMaxBaseAmount = new Token();
        coinMaxBaseAmount.setDenom(denomMaxBaseAmount);
        coinMaxBaseAmount.setAmount(Utils.NewDecString(amountMaxBaseAmount));

        Token coinQuoteAmount = new Token();
        coinQuoteAmount.setDenom(denomQuoteAmount);
        coinQuoteAmount.setAmount(Utils.NewDecString(amountQuoteAmount));

        MsgAddLiquidityValue value = new MsgAddLiquidityValue();

        value.setDeadline(deadline);
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
