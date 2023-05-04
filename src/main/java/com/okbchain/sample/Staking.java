package com.okbchain.sample;

import com.okbchain.env.EnvInstance;
import com.okbchain.msg.distribution.MsgModifyWithdrawAddress;
import com.okbchain.msg.distribution.MsgWithdrawReward;
import com.okbchain.msg.slashing.MsgUnjail;
import com.okbchain.msg.staking.*;
import com.okbchain.utils.Utils;
import com.okbchain.utils.crypto.Crypto;
import com.okbchain.utils.crypto.PrivateKey;
import com.okbchain.msg.*;
import com.okbchain.msg.tx.BroadcastTx;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.tx.UnsignedTx;
import com.okbchain.msg.common.Signature;

public class Staking {

    public static void main(String[] args) {
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        EnvInstance.getEnv().setChainID("okbchainevm-8");
        EnvInstance.getEnv().setDenom("okt");

//        testUnjail();
//        testBindProxy();
        testMsgUnbindProxy();
//        testEditValidator();
//        testMsgDeposit();
        testMsgWithdrawStaking();
//        testMsgAddShares();
//        testRegProxy();
//        testWithdrawReward();
//        testModifyWithdrawAddress();
//        testMsgDestroyValidator();
    }

    static void testBindProxy() {
//        String mnemonic = "puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer";
//        String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
//        System.out.println(prikey);

        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgBindProxy msg = new MsgBindProxy();
        msg.init(key.getPubKey());

        String delegator_address = key.getAddress();
        String proxy_address = "okbchain193xnjknz3e52mqv2nyufnzjugu3mh65rpxdasn";

        Message messages = msg.produceMsg(delegator_address, proxy_address);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgUnbindProxy() {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgUnbindProxy msg = new MsgUnbindProxy();
        msg.init(key.getPubKey());

        Message messages = msg.produce();

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testEditValidator(){
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgEditValidator msg = new MsgEditValidator();
        msg.init(key.getPubKey());

        Message messages = msg.produceMsg("1","1","1","1", Crypto.generateValidatorAddressFromPub(key.getPubKey()));

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgDeposit() {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");
        MsgDeposit msg = new MsgDeposit();
        msg.init(key.getPubKey());

        Message messages = msg.produceMsg(EnvInstance.getEnv().GetDenom(), Utils.NewDecString("10"), key.getAddress());

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgWithdrawStaking() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgWithdrawStaking msg = new MsgWithdrawStaking();
        msg.init(key.getPubKey());

        Message messages = msg.produceMsg(EnvInstance.getEnv().GetDenom(), "9.000000000000000000", key.getAddress());

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgAddShares() {
        PrivateKey key = new PrivateKey("3040196C06C630C1E30D6D347B097C9EA64ADA24FB94823B6C755194F3A00761");
        PrivateKey key1 = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");
        MsgAddShares msg = new MsgAddShares();
        msg.init(key.getPubKey());

        String [] validators = { Crypto.generateValidatorAddressFromPub(key1.getPubKey()) };
        Message messages = msg.produceMsg(key.getAddress(), validators);


        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testRegProxy() {
        PrivateKey key = new PrivateKey("3040196C06C630C1E30D6D347B097C9EA64ADA24FB94823B6C755194F3A00761");
        MsgRegProxy msg = new MsgRegProxy();
        msg.init(key.getPubKey());

        Message messages = msg.produceMsg(key.getAddress(), true);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testUnjail() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgUnjail msg = new MsgUnjail();
        msg.init(key.getPubKey());

        Message messages = msg.produceMsg(Crypto.generateValidatorAddressFromPub(key.getPubKey()));

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testWithdrawReward() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgWithdrawReward msg = new MsgWithdrawReward();
        msg.init(key.getPubKey());

        Message messages = msg.produceWithdrawRewardMsg(Crypto.generateValidatorAddressFromPub(key.getPubKey()));

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testModifyWithdrawAddress() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgModifyWithdrawAddress msg = new MsgModifyWithdrawAddress();
        msg.init(key.getPubKey());

        Message messages = msg.produceModifyWithdrawAddressMsg("okbchain1twtrl3wvaf9yz6jvt4s726wj6e3cpfxxlgampg");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgDestroyValidator() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgDestroyValidator msg = new MsgDestroyValidator();
        msg.init(key.getPubKey());

        Message messages = msg.produceMsg();

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
