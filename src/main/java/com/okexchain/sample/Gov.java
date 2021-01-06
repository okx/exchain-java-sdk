package com.okexchain.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.gov.MsgDeListProposal;
import com.okexchain.msg.gov.MsgParameterChangeProposal;
import com.okexchain.msg.gov.MsgVote;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.tx.BroadcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.crypto.PrivateKey;

public class Gov {

    public static void main(String[] args) throws JsonProcessingException {
        EnvInstance.setEnv(new EnvBase());

//        testParameterChangeProposal();
//        testDeListProposal();
        testVote();
    }

    static void testParameterChangeProposal() throws JsonProcessingException {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgParameterChangeProposal msg = new MsgParameterChangeProposal();
        msg.init(key.getPubKey());

        Message messages = msg.produceParameterChangeProposalMsg(
                "param change of mint deflation_rate",
                "change deflation_rate from 0.5 to 0.6",
                "staking",
                "MaxValidators",
                "",
                "121",
                "3500",
                "1000"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testDeListProposal() throws JsonProcessingException {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgDeListProposal msg = new MsgDeListProposal();
        msg.init(key.getPubKey());

        Message messages = msg.produceDelistProposalMsg(
                "delete token pair proposal",
                "delete eos-3bd_okt",
                "eos-3bd",
                EnvInstance.getEnv().GetDenom(),
                "100"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testVote() {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgVote msg = new MsgVote();
        msg.init(key.getPubKey());

        Message messages = msg.produceVoteMsg(
                "2",
                "Yes"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
