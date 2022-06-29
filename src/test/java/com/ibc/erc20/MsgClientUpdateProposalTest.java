package com.ibc.erc20;


import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.MsgClientUpdateProposal;
import com.okexchain.msg.gov.MsgClientUpdateProposalValue;
import com.okexchain.utils.crypto.PrivateKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgClientUpdateProposalTest {

    @Test
    public void testProduceClientUpdateProposal(){
        EnvInstance.getEnvTestNet();
        MsgClientUpdateProposal msg=new MsgClientUpdateProposal();
        msg.init(new PrivateKey("7fd8c2db0ad8f99f6d33a5e92a51a71be585d891176bd0338e5a6be64c4ff982"));

        MsgClientUpdateProposalValue value=new MsgClientUpdateProposalValue();
        value.setTitle("title");
        value.setDescription("des");
        value.setSubjectClientId("c1");
        value.setSubstituteClientId("c2");

        Message message=msg.produceClientUpdateProposal(value,"okt","0.050000000000000000");
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
