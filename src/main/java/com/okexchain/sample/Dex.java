package com.okexchain.sample;
import com.okexchain.env.EnvInstance;
import com.okexchain.env.LocalEnv;
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
//        testMsgTransferTokenPairOwnership();
        testMsgConfirmTokenPairOwnership();
    }

    static void testMsgCreateOperator(){
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgCreateOperator msg = new MsgCreateOperator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceCreateOperatorMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "https://captain.okg/operator.json");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain dex create operator!");
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

    static void testMsgTransferTokenPairOwnership(){
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgTransferTokenPairOwnership msg = new MsgTransferTokenPairOwnership();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceTransferTokenPairOwnershipMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "okexchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptddw77gw",
                "eos-654_okt"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer token pair ownership");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgConfirmTokenPairOwnership(){
        PrivateKey key = new PrivateKey("29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884");

        MsgConfirmTokenPairOwnership msg = new MsgConfirmTokenPairOwnership();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceConfirmTokenPairOwnershipMsg(
                "okexchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptddw77gw",
                "eos-654_okt"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain confirm token pair ownership");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}