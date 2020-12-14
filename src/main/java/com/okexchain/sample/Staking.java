package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.Crypto;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.msg.*;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.common.Signature;

public class Staking {

    public static void main(String[] args) {
//        testUnjail();
//        testBindProxy();
//        testEditValidator();
//        testMsgDeposit();
//        testMsgWithdrawStaking();
//        testMsgAddShares();
//        testRegProxy();
//        testWithdrawReward();
        testModifyWithdrawAddress();
    }

    static void testBindProxy() {
//        String mnemonic = "puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer";
//        String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
//        System.out.println(prikey);

        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgBindProxy msg = new MsgBindProxy();
        msg.init(key.getAddress(), key.getPubKey());

        String delegator_address = key.getAddress();
        String proxy_address = "okexchain1ntvyep3suq5z7789g7d5dejwzameu08m6gh7yl";

        Message messages = msg.produceMsg(delegator_address, proxy_address);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testEditValidator(){
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgEditValidator msg = new MsgEditValidator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg("1","1","1","1", Crypto.generateValidatorAddressFromPub(key.getPubKey()));

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgDeposit() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgDeposit msg = new MsgDeposit();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg(EnvInstance.getEnv().GetDenom(), "10.000000000000000000", key.getAddress());

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgWithdrawStaking() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgWithdrawStaking msg = new MsgWithdrawStaking();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg(EnvInstance.getEnv().GetDenom(), "9.000000000000000000", key.getAddress());

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgAddShares() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgAddShares msg = new MsgAddShares();
        msg.init(key.getAddress(), key.getPubKey());

        String [] validators = { Crypto.generateValidatorAddressFromPub(key.getPubKey()) };
        Message messages = msg.produceMsg(key.getAddress(), validators);


        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testRegProxy() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgRegProxy msg = new MsgRegProxy();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg(key.getAddress(), true);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testUnjail() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgUnjail msg = new MsgUnjail();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceMsg(Crypto.generateValidatorAddressFromPub(key.getPubKey()));

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testWithdrawReward() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgWithdrawReward msg = new MsgWithdrawReward();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceWithdrawRewardMsg(Crypto.generateValidatorAddressFromPub(key.getPubKey()));

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testModifyWithdrawAddress() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgModifyWithdrawAddress msg = new MsgModifyWithdrawAddress();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceModifyWithdrawAddressMsg("okexchain1twtrl3wvaf9yz6jvt4s726wj6e3cpfxxlgampg");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
