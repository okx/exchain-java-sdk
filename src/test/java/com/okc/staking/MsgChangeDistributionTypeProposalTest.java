package com.okc.staking;


import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.gov.MsgChangeDistributionTypeProposal;
import com.okbchain.msg.gov.MsgChangeDistributionTypeProposalValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgChangeDistributionTypeProposalTest {

    //https://www.oklink.com/zh-cn/okc-test/tx/0x85C2B7CAE5B699CB6B5B5A034DBD6B9E3775BF4667E6597E689FB38857DDA71E setType=0
    //https://www.oklink.com/zh-cn/okc-test/tx/0xC227ADE88CC3A1F5FD4FFE6A977CD20139B27A57723F490C8CD628DDFF1994D9 setType=1
    @Test
    public void testProduceMsgChangeDistributionTypeProposal(){

        MsgChangeDistributionTypeProposal msg=new MsgChangeDistributionTypeProposal();
        EnvInstance.getEnvTestNet();
        msg.initMnemonic("");
        MsgChangeDistributionTypeProposalValue proposalValue=new MsgChangeDistributionTypeProposalValue();
        proposalValue.setTitle("test ChangeDistributionTypeProposal");
        proposalValue.setDescription("desc");
        proposalValue.setType(1);

        Message message=msg.produceMsgChangeDistributionTypeProposal(proposalValue,"okt","10.000000000000000000");
        JSONObject res = msg.submit(message, "0.03", "20000000", "");
        System.out.println(res.toJSONString());
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
