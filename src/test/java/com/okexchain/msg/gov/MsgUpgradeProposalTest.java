package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import junit.framework.TestCase;
import org.junit.Test;

public class MsgUpgradeProposalTest {

    @Test
    public void testProposal() {
        EnvBase env = EnvInstance.getEnvTestNet();
        env.setRestServerUrl("http://18.178.135.201:26659");
        env.setRestPathPrefix("/exchain/v1");
        env.setTxUrlPath("/exchain/v1/txs");
//        EnvBase envBase = EnvInstance.getEnvLocalNet();
        try {
            MsgUpgradeProposal.ProposalValue proposalValue = new MsgUpgradeProposal.ProposalValue("upgradeProposalTest","upgrade","UnvoteTest10","20049719",null);
            MsgUpgradeProposal proposal = new MsgUpgradeProposal(proposalValue);
            proposal.initMnemonic("tree allow unlock rug enact senior laundry aunt festival lab jungle ill");
            Message message = proposal.buildMessage("okt","10");
            JSONObject res = proposal.submit(message, "0.05000000", "500000", "");
            System.out.println(res.toString());
            boolean succeed = proposal.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}