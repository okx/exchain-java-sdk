package com.okexchain.sample;
import com.okexchain.env.EnvInstance;
import com.okexchain.env.LocalEnv;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.msg.*;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.common.Signature;


public class Dex {
    public static void main(String[] args) {
        EnvInstance.setEnv(new LocalEnv("http://localhost:26659"));

        //testMsgCreateOperator();
        //testMsgUpdateOperator();
        //testMsgList();
        testMsgTransferTradingPairOwnership();
    }

    static void testMsgCreateOperator(){
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgCreateOperator msg = new MsgCreateOperator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceCreateOperatorMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "https://captain.okg/operator.json");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "okexchain dex create operator!");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgUpdateOperator(){
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgUpdateOperator msg = new MsgUpdateOperator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceUpdateOperatorMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
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
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgList msg = new MsgList();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceListMsg(
                "eos-d87",
                "okt",
                "1.00000000");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain dex list!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgTransferTradingPairOwnership(){
        PrivateKey keyFrom = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
        PrivateKey keyTo = new PrivateKey("f4feaca3a36f824776786eda67d4e12d5eeb038cc48c002d650fd2ede6b42a1b");

        MsgTransferTradingPairOwnership msg = new MsgTransferTradingPairOwnership();
        msg.init(keyFrom.getAddress(), keyFrom.getPubKey());
        String toAddress = "okexchain106gw9etrg2650l04w7zvcax2ytxelx7yp7fzck";
        String product = "eos-d87_okt";

        try {
            Message messages = msg.produceMsg(toAddress, product);
            String msgJson = msg.toJson(messages);
            Signature toSignature = MsgBase.signTx(msgJson, keyTo.getPriKey());
            messages = msg.setToSignature(messages, toSignature);

            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "dex transfer ownership");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), keyFrom.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}