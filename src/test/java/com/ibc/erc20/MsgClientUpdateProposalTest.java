package com.ibc.erc20;


import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.gov.MsgClientUpdateProposal;
import com.okbchain.msg.gov.MsgClientUpdateProposalValue;
import com.okbchain.utils.crypto.PrivateKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgClientUpdateProposalTest {

    @Test
    public void testProduceClientUpdateProposal(){
        EnvInstance.getEnvTestNet();
        MsgClientUpdateProposal msg=new MsgClientUpdateProposal();
        msg.init(new PrivateKey(""));

        MsgClientUpdateProposalValue value=new MsgClientUpdateProposalValue();
        value.setTitle("title");
        value.setDescription("des");
        value.setSubjectClientId("07-tendermint-0");
        value.setSubstituteClientId("07-tendermint-1");

        Message message=msg.produceClientUpdateProposal(value,"okt","20.000000000000000000");
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
