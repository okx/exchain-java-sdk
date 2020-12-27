package com.okexchain.msg.ammswap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

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

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgRemoveLiquidity msg = new MsgRemoveLiquidity();
        msg.init(key);

        Message messages = msg.produce(
                "PT10S",
                "1",
                "80",
                "okt",
                "100",
                "usdk-5f7"
        );
        JSONObject res = msg.submit(messages, "0.05", "500000", "");

        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
