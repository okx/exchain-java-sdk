package com.okexchain.sample;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.gov.*;
import com.okexchain.msg.tx.BroadcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.crypto.PrivateKey;

public class Gov {

    public static void main(String[] args) throws JsonProcessingException {
        EnvInstance.setEnv(new EnvBase());

//        testParameterChangeProposal();
//        testDeListProposal();
        testVote();
        testContractBlockedListProposal();
        testContractBlockedListProposalValue();
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

    public static void testContractBlockedListProposal() {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        env.setDenom("okt");

//        {"codespace":"sdk","code":4,"gas_used":"67065","gas_wanted":"2000000","raw_log":"unauthorized: signature verification failed; verify correct account sequence and chain-id, sign msg:{\"account_number\":\"2\",\"chain_id\":\"okexchainevm-8\",\"fee\":{\"amount\":[{\"amount\":\"0.030000000000000000\",\"denom\":\"okt\"}],\"gas\":\"2000000\"},\"memo\":\"\",\"msgs\":[{\"type\":\"okexchain/gov/MsgSubmitProposal\",\"value\":{\"content\":{\"type\":\"okexchain/evm/ManageContractBlockedListProposal\",\"value\":{\"contract_addresses\":[\"okexchain1hw4r48aww06ldrfeuq2v438ujnl6alsz0685a0\",\"okexchain1qj5c07sm6jetjz8f509qtrxgh4psxkv32x0qas\"],\"description\":\"String description\",\"is_added\":true,\"title\":\"String title\"}},\"initial_deposit\":[{\"amount\":\"100.000000000000000000\",\"denom\":\"okt\"}],\"proposer\":\"okexchain1qpel9c5wlrc30efaskqfgzrda7h3sd745rcxeh\"}}],\"sequence\":\"9\"}","height":"0","txhash":"8240A3B7734DEB2878BD629CEB2426E04E1D4E96C3A3E7E87CB1933DD2FD0A49"}


        MsgContractBlockedListProposal msg = new MsgContractBlockedListProposal();
        msg.init(new PrivateKey("75dee45fc7b2dd69ec22dc6a825a2d982aee4ca2edd42c53ced0912173c4a788".toUpperCase()));

        String[] contractAddresses = new String[]{"okexchain1hw4r48aww06ldrfeuq2v438ujnl6alsz0685a0","okexchain1qj5c07sm6jetjz8f509qtrxgh4psxkv32x0qas"};

        Message messages = msg.produceContractDeploymentWhitelistProposal(
                "String title",
                "String description",
                contractAddresses,
                true,
                "okt",
                "100.000000000000000000"
        );

        JSONObject res = msg.submit(messages, "0.03", "2000000", "");
        System.out.println(res.toJSONString());
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void testContractBlockedListProposalValue() {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        env.setDenom("okt");


        MsgContractDeploymentWhitelistProposal msg = new MsgContractDeploymentWhitelistProposal();
        msg.init(new PrivateKey("75dee45fc7b2dd69ec22dc6a825a2d982aee4ca2edd42c53ced0912173c4a788".toUpperCase()));

        String[] distributorAddresses = new String[]{"okexchain1hw4r48aww06ldrfeuq2v438ujnl6alsz0685a0","okexchain1qj5c07sm6jetjz8f509qtrxgh4psxkv32x0qas"};

        Message messages = msg.produceContractDeploymentWhitelistProposal(
                "String title",
                "String description",
                distributorAddresses,
                true,
                "okt",
                "100.000000000000000000"
        );

        JSONObject res = msg.submit(messages, "0.03", "2000000", "");
        System.out.println(res.toJSONString());
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
