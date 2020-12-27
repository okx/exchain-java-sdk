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

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgSwapToken msg = new MsgSwapToken();
        msg.init(key);

        Message messages = msg.produceMsg(
                "PT10S",
                "okexchain1s6nfs7mlj7ewsskkrmekqhpq2w234fcz9sp3uz",
                "10",
                "okt",
                "15",
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
