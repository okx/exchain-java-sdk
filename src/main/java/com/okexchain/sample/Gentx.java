package com.okexchain.sample;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.MsgCreateOKValidator;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.utils.crypto.Crypto;

public class Gentx {
    public static void main(String[] args) {
//        String priKey = Crypto.generatePrivateKeyFromMnemonic("race imitate stay curtain puppy suggest spend toe old bridge sunset pride");
        PrivateKey key = new PrivateKey("b4083733cd8379f1249cb9431a074e495a64ae003273d31a7e58356eaad8a0cf");

        MsgCreateOKValidator msg = new MsgCreateOKValidator();
        System.out.println(key.getPubKey());
        msg.init(key.getPubKey(),"0","0");
        msg.setMsgType("okexchain/staking/MsgCreateValidator");


        Message messages = msg.produceMsg("okexchainvalconspub1zcjduepqtv2yy90ptjegdm34vfhlq2uw9eu39hjrt98sffj7yghl4s47xv7svt56mk",
                "val0","","","","10000.00000000");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            System.out.println("================");
            System.out.println(Utils.serializer.toJson(signedTx.getTx()));
            System.out.println("================");
            System.out.println(signedTx.toJson());
//            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
