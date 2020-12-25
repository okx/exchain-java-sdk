package com.okexchain.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.MsgCreateOperator;
import com.okexchain.msg.MsgTokenIssue;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.crypto.PrivateKey;

public class Token {

    public static void main(String[] args) throws JsonProcessingException {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("okexchainevm-8");
        env.setRestServerUrl("http://localhost:8545");
        testMsgIssueToken();
    }

    static void testMsgIssueToken(){
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgTokenIssue msg = new MsgTokenIssue();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceTokenIssueMsg(
                "usdk",
                "usdk",
                "usdk",
                "usdk",
                "1000000000",
                key.getAddress(),
                true
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
