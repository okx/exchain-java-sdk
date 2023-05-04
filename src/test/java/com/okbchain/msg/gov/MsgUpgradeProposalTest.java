package com.okbchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvBase;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import org.junit.Test;

public class MsgUpgradeProposalTest {

    @Test
    public void testProposal() {
        EnvBase env = EnvInstance.getEnvLocalNet();
        try {
            MsgUpgradeProposal.ProposalValue proposalValue = new MsgUpgradeProposal.ProposalValue("upgradeProposalTest","upgrade","UnvoteTest10","1000",null);
            MsgUpgradeProposal proposal = new MsgUpgradeProposal(proposalValue);
            proposal.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
            Message message = proposal.buildMessage("okb","10");
            JSONObject res = proposal.submit(message, "0.05000000", "500000", "");
            System.out.println(res.toString());
            boolean succeed = proposal.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}