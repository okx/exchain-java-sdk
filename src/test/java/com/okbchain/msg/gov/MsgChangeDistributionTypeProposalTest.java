package com.okbchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvBase;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import org.junit.Test;

public class MsgChangeDistributionTypeProposalTest  {


    @Test
    public void testMsgChangeDistributionTypeProposal() {
        EnvBase env = EnvInstance.getEnvLocalNet();
        try {
            MsgChangeDistributionTypeProposal proposal = new MsgChangeDistributionTypeProposal();
            MsgChangeDistributionTypeProposalValue proposalValue = new MsgChangeDistributionTypeProposalValue("test","test",1);
            proposal.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

            Message message = proposal.produceMsgChangeDistributionTypeProposal(proposalValue,"okb","10");
            JSONObject res = proposal.submit(message, "0.05000000", "500000", "");
            System.out.println(res.toString());
            boolean succeed = proposal.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}