package com.ibc.erc20;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.MsgProxyContractRedirectProposal;
import com.okexchain.msg.gov.MsgProxyContractRedirectProposalValue;
import com.okexchain.utils.crypto.PrivateKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgProxyContractRedirectProposalTest {

    @Test
    public void testProduceProxyContractRedirectProposal(){
        EnvInstance.getEnvTestNet();
        MsgProxyContractRedirectProposal msg=new MsgProxyContractRedirectProposal();
        msg.init(new PrivateKey("7fd8c2db0ad8f99f6d33a5e92a51a71be585d891176bd0338e5a6be64c4ff982"));

        MsgProxyContractRedirectProposalValue proposal=new MsgProxyContractRedirectProposalValue();
        proposal.setTitle("ProxyContractRedirectProposal");
        proposal.setDescription("update contract owner");
        proposal.setType(1);
        proposal.setDenom("ibc/34343");
        proposal.setAddr("0xFc909f43A85e80135f46d4c493B177cFE4CF3514");

        Message message=msg.produceProxyContractRedirectProposal(proposal,"okt","0.050000000000000000");
        JSONObject res = msg.submit(message, "0.03", "2000000", "");
        System.out.println(res.toJSONString());
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
