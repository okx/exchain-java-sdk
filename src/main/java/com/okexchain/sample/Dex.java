package com.okexchain.sample;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.dex.MsgWithdrawDeposit;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.msg.*;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.common.Signature;


public class Dex {
    public static void main(String[] args) {
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setDenom("okt");

//        testMsgCreateOperator();
//        testMsgUpdateOperator();
//        testMsgList();
//        testMsgTransferTokenPairOwnership();
//        testMsgConfirmTokenPairOwnership();
        testMsgWithdrawDeposit();

    }

    static void testMsgCreateOperator(){
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgCreateOperator msg = new MsgCreateOperator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceCreateOperatorMsg(
                key.getAddress(),
                "https://bob.okg/operator.json");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "okexchain dex create operator!");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgUpdateOperator(){
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgUpdateOperator msg = new MsgUpdateOperator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceUpdateOperatorMsg(
                "okexchain1ntvyep3suq5z7789g7d5dejwzameu08m6gh7yl",
                "https://captain.okg/operator111.json");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain dex create operator!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgList(){
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgList msg = new MsgList();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceListMsg(
                "usdk-5f7",
                EnvInstance.getEnv().GetDenom(),
                "1");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.02000000", "200000", "okexchain dex list!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgTransferTokenPairOwnership(){
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgTransferTokenPairOwnership msg = new MsgTransferTokenPairOwnership();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceTransferTokenPairOwnershipMsg(
                "okexchain1twtrl3wvaf9yz6jvt4s726wj6e3cpfxxlgampg",
                "okexchain1ntvyep3suq5z7789g7d5dejwzameu08m6gh7yl",
                "eos-3bd_okt"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer token pair ownership!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgConfirmTokenPairOwnership(){
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgConfirmTokenPairOwnership msg = new MsgConfirmTokenPairOwnership();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceConfirmTokenPairOwnershipMsg(
                "okexchain1ntvyep3suq5z7789g7d5dejwzameu08m6gh7yl",
                "eos-3bd_okt"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain confirm token pair ownership!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgWithdrawDeposit(){
        PrivateKey key = new PrivateKey("3040196C06C630C1E30D6D347B097C9EA64ADA24FB94823B6C755194F3A00761");

        MsgWithdrawDeposit msg = new MsgWithdrawDeposit();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produce(
                "okt",
                "1000",
                "eos-c38_okt"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "withdraw deposit!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}