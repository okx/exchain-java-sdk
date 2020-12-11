package com.okexchain.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvInstance;
import com.okexchain.env.LocalEnv;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.MsgDeListProposal;
import com.okexchain.msg.MsgParameterChangeProposal;
import com.okexchain.msg.MsgVote;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.types.MsgSubmitProposalValue;
import com.okexchain.utils.crypto.PrivateKey;

public class Gov {

    public static void main(String[] args) throws JsonProcessingException {
        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));

//        testParameterChangeProposal();
//        testDeListProposal();
        testVote();
    }

    static void testParameterChangeProposal() throws JsonProcessingException {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgParameterChangeProposal msg = new MsgParameterChangeProposal();
        msg.init(key.getAddress(), key.getPubKey());

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

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testDeListProposal() {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgDeListProposal msg = new MsgDeListProposal();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceDelistProposalMsg(
                "delete token pair proposal",
                "delete eos-3bd_tokt",
                "eos-3bd",
                "tokt",
                "100"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testVote() {
        PrivateKey key = new PrivateKey("17157D973569415C616E70BE2537DFB9F48BAD5C7FF088A5FCDF193DD3E450E3");

        MsgVote msg = new MsgVote();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceVoteMsg(
                "2",
                "Yes"
        );

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
