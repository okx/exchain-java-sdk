package com.okbchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import org.junit.Test;

public class MsgContractDeploymentWhitelistProposalTest{

    @Test
    public void testMsgContractDeploymentWhitelistProposal() {
        EnvInstance.getEnvLocalNet();
        MsgContractDeploymentWhitelistProposal msg=new MsgContractDeploymentWhitelistProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        MsgContractDeploymentWhitelistProposalValue proposalValue = new MsgContractDeploymentWhitelistProposalValue();
        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setDistributorAddresses(new String[]{"ex1fsfwwvl93qv6r56jpu084hxxzn9zphnyxhske5"});
        proposalValue.setIsAdded(true);

        Message message = msg.produceContractDeploymentWhitelistProposal(proposalValue, "okb", "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}