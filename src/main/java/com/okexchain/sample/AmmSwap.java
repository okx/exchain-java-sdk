package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.ammswap.MsgAddLiquidity;
import com.okexchain.msg.ammswap.MsgCreateExchange;
import com.okexchain.msg.ammswap.MsgRemoveLiquidity;
import com.okexchain.msg.ammswap.MsgSwapToken;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.crypto.PrivateKey;

public class AmmSwap {

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setDenom("okt");

//        testAddLiquidity();
        testRemoveLiquidity();
//        testCreateExchange();
//        testSwapToken();
    }

    static void testAddLiquidity() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgAddLiquidity msg = new MsgAddLiquidity();
        msg.init(key.getAddress(), key.getPubKey());

        // the lexicographic order of BaseDenom must be less than QuoteDenom
        Message messages = msg.produceMsg(
                "PT10S",
                "1",
                "100",
                "okt",
                "100",
                "usdk-5f7"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "add liquidity!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testRemoveLiquidity() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgRemoveLiquidity msg = new MsgRemoveLiquidity();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produce(
                "PT10S",
                "1",
                "80",
                "okt",
                "100",
                "usdk-5f7"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "remove liquidity!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testCreateExchange() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgCreateExchange msg = new MsgCreateExchange();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.productMsg(
                "usdk-5f7",
                "okt"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "create exchange!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testSwapToken() {

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgSwapToken msg = new MsgSwapToken();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg(
                "PT10S",
                "okexchain1s6nfs7mlj7ewsskkrmekqhpq2w234fcz9sp3uz",
                "10",
                "okt",
                "15",
                "usdk-5f7"

        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "swap token!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
