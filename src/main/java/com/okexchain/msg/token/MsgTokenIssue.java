package com.okexchain.msg.token;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgTokenIssue extends MsgBase {

    public MsgTokenIssue() {
        setMsgType("okexchain/token/MsgIssue");
    }

    public Message produceTokenIssueMsg(String description, String symbol, String originalSymbol, String wholeName, String totalSupply, String owner, boolean mintable) {

        MsgTokenIssueValue value = new MsgTokenIssueValue();
        value.setDescription(description);
        value.setSymbol(symbol);
        value.setOriginalSymbol(originalSymbol);
        value.setWholeName(wholeName);
        value.setTotalSupply(totalSupply);
        value.setOwner(owner);
        value.setMintable(mintable);

        Message<MsgTokenIssueValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public String getIssuedToken(JSONObject result) throws Exception {
        try {
            return getMatchedAttribute(result, "symbol");
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgTokenIssue msg = new MsgTokenIssue();
        msg.init(key);

        Message messages = msg.produceTokenIssueMsg(
                "usdk",
                "usdk",
                "usdk",
                "usdk",
                "1000000000",
                key.getAddress(),
                true
        );
        JSONObject res = msg.submit(messages, "0.05000000", "500000", "");

        String token = "";
        try {
            token = msg.getIssuedToken(res);
            System.out.println("issued token: " +  token);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}