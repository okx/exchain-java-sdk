package com.okexchain.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.common.TransferUnits;
import com.okexchain.msg.token.MsgMultiTransfer;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;

public class Token {

    public static void main(String[] args) throws JsonProcessingException {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("okexchainevm-8");
        env.setDenom("okt");

//        testMultiTransfer();
    }


    static void testMultiTransfer() {
        PrivateKey key = new PrivateKey("3040196C06C630C1E30D6D347B097C9EA64ADA24FB94823B6C755194F3A00761");

        MsgMultiTransfer msg = new MsgMultiTransfer();
        msg.init(key.getPubKey());

        List<com.okexchain.msg.common.Token> tokens = new ArrayList<>();
        com.okexchain.msg.common.Token amount = new com.okexchain.msg.common.Token();
        amount.setAmount(Utils.NewDecString("10"));
        amount.setDenom("okt");
        tokens.add(amount);

        List<TransferUnits> transferUnits = new ArrayList<>();
        TransferUnits transferUnit = new TransferUnits();
        transferUnit.setCoins(tokens);
        transferUnit.setTo("okexchain1twtrl3wvaf9yz6jvt4s726wj6e3cpfxxlgampg");
        transferUnits.add(transferUnit);

        Message messages = msg.produceMsg(
                transferUnits
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "multi transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
