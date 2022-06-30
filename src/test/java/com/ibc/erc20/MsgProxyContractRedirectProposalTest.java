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
        msg.init(new PrivateKey(""));

        MsgProxyContractRedirectProposalValue proposal=new MsgProxyContractRedirectProposalValue();
        proposal.setTitle("ProxyContractRedirectProposal");
        proposal.setDescription("update contract owner");
        proposal.setType("1");
        proposal.setDenom("ibc/9117a26ba81e29fa4f78f57dc2bd90cd3d26848101ba880445f119b22a1e254e");
        proposal.setAddr("0xbbE4733d85bc2b90682147779DA49caB38C0aA1F");

        Message message=msg.produceProxyContractRedirectProposal(proposal,"okt","1.000000000000000000");
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
