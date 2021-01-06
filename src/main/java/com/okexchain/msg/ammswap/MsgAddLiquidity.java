package com.okexchain.msg.ammswap;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.time.Duration;
import java.time.Instant;

public class MsgAddLiquidity extends MsgBase {

    public MsgAddLiquidity() { setMsgType("okexchain/ammswap/MsgAddLiquidity"); }

    public Message produceMsg(String deadline, String minLiquidity, String maxBaseAmount, String baseDenom, String quoteAmount, String quoteDenom) {

        Token maxBaseToken = new Token();
        maxBaseToken.setDenom(baseDenom);
        maxBaseToken.setAmount(Utils.NewDecString(maxBaseAmount));

        Token quoteToken = new Token();
        quoteToken.setDenom(quoteDenom);
        quoteToken.setAmount(Utils.NewDecString(quoteAmount));

        MsgAddLiquidityValue value = new MsgAddLiquidityValue();

        long current = Instant.now().getEpochSecond() + Duration.parse(deadline).getSeconds();
        value.setDeadline(Long.toString(current));
        value.setSender(this.address);
        value.setMinLiquidity(Utils.NewDecString(minLiquidity));
        value.setMaxBaseAmount(maxBaseToken);
        value.setQuoteAmount(quoteToken);

        Message<MsgAddLiquidityValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgAddLiquidity msg = new MsgAddLiquidity();
        msg.init(key);

        // the lexicographic order of BaseDenom must be less than QuoteDenom
        Message messages = msg.produceMsg(
                "PT10S",
                "0.5",
                "100",
                "okt",
                "100",
                "usdk-5f7"
        );
        JSONObject res = msg.submit(messages, "0.05", "500000", "add liquidity!");

        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
