package com.okbchain.sample;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.staking.MsgCreateValidator;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Signature;
import com.okbchain.msg.tx.BroadcastValue;
import com.okbchain.msg.tx.UnsignedTx;
import com.okbchain.utils.crypto.PrivateKey;

public class Gentx {
    public static void main(String[] args) {
//        String priKey = Crypto.generatePrivateKeyFromMnemonic("race imitate stay curtain puppy suggest spend toe old bridge sunset pride");
        PrivateKey key = new PrivateKey("b4083733cd8379f1249cb9431a074e495a64ae003273d31a7e58356eaad8a0cf");

        MsgCreateValidator msg = new MsgCreateValidator();
        System.out.println(key.getPubKey());
        msg.init(key.getPubKey(),"0","0");

        Message messages = msg.produceMsg(
                "okbchainvalconspub1zcjduepqtv2yy90ptjegdm34vfhlq2uw9eu39hjrt98sffj7yghl4s47xv7svt56mk",
                "val0","","","", "10000.00000000");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());
            BroadcastValue signedTx = unsignedTx.sign4gentx(signature);

            System.out.println("======= gentx json =========");
            System.out.println(signedTx.toJson());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
