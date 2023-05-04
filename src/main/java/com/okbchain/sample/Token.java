package com.okbchain.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okbchain.env.EnvBase;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.TransferUnit;
import com.okbchain.msg.token.MsgMultiTransfer;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Token {

    public static void main(String[] args) throws JsonProcessingException {
        EnvBase env = EnvInstance.getEnv();

        env.setChainID("exchain-101");
//        env.setTxUrlPath("/cosmos/tx/v1beta1/txs");
        env.setRestServerUrl("http://127.0.0.1:36659");
        env.setRestPathPrefix("/exchain/v1");
        env.setTxUrlPath("/exchain/v1/txs");

        testMultiTransfer();
    }


    static void testMultiTransfer() {
        MsgMultiTransfer msg = new MsgMultiTransfer();
        msg.initMnemonic("giggle sibling fun arrow elevator spoon blood grocery laugh tortoise culture tool");

        List<com.okbchain.msg.common.Token> tokens = new ArrayList<>();
        com.okbchain.msg.common.Token amount = new com.okbchain.msg.common.Token();
        amount.setAmount(Utils.NewDecString("10"));
        amount.setDenom("okt");
        tokens.add(amount);
        com.okbchain.msg.common.Token amount1 = new com.okbchain.msg.common.Token();
        amount1.setAmount(Utils.NewDecString("10"));
        amount1.setDenom("usdk");
        tokens.add(amount1);

        List<TransferUnit> transfers = new ArrayList<>();

        TransferUnit transferUnit = new TransferUnit();
        transferUnit.setCoins(tokens);
        transferUnit.setTo("okbchain1twtrl3wvaf9yz6jvt4s726wj6e3cpfxxlgampg");
        transfers.add(transferUnit);

        TransferUnit transferUnit1 = new TransferUnit();
        transferUnit1.setCoins(tokens);
        transferUnit1.setTo("okbchain1twtrl3wvaf9yz6jvt4s726wj6e3cpfxxlgampg");
        transfers.add(transferUnit1);

        Message messages = msg.produceMsg(
                transfers
        );

        try {
            System.out.println(msg.submit(messages, "0.03", "2000000", ""));
        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
