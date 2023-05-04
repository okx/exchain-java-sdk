package com.okbchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvBase;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import org.junit.Test;

public class MsgModifyNextBlockUpdateProposalTest{

    @Test
    public void testSubmitProposal() {
        EnvBase env = EnvInstance.getEnvLocalNet();
        try {

            MsgModifyNextBlockUpdateProposal.ProposalValue proposalValue = new MsgModifyNextBlockUpdateProposal.ProposalValue("ModifyNextBlock", "ModifyNextBlock","10002");
            MsgModifyNextBlockUpdateProposal proposal = new MsgModifyNextBlockUpdateProposal(proposalValue);
            proposal.initMnemonic("");
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