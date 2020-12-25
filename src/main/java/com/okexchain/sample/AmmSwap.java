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
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setDenom("tokt");

//        testAddLiquidity();
//        testRemoveLiquidity();
        testCreateExchange();
//        testSwapToken();
    }

    static void testAddLiquidity() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgAddLiquidity msg = new MsgAddLiquidity();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg(
                "100000000",
                "3",
                "100",
                "eth-aeb",
                "100",
                "tokt"
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
        PrivateKey key = new PrivateKey("3040196C06C630C1E30D6D347B097C9EA64ADA24FB94823B6C755194F3A00761");

        MsgRemoveLiquidity msg = new MsgRemoveLiquidity();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produce(
                10,
                "10",
                "1",
                "okt",
                "1",
                "eos"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "remove liquidity!");

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
                "eth-aeb",
                "tokt"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "create exchange!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testSwapToken() {

        PrivateKey key = new PrivateKey("3040196C06C630C1E30D6D347B097C9EA64ADA24FB94823B6C755194F3A00761");

        MsgSwapToken msg = new MsgSwapToken();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg(
                "10s",
                "okexchain1s6nfs7mlj7ewsskkrmekqhpq2w234fcz9sp3uz",
                "1",
                "tokt",
                "1,",
                "eos-c38"

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
