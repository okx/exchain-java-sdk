package com.ibc.erc20;


import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.MsgTokenMappingProposal;
import com.okexchain.msg.gov.MsgTokenMappingProposalValue;
import com.okexchain.utils.crypto.PrivateKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
@Slf4j
public class MsgTokenMappingProposalTest {


    @Test
    public void testProduceTokenMappingProposal(){
        EnvInstance.getEnvTestNet();
        MsgTokenMappingProposal msg=new MsgTokenMappingProposal();
        msg.init(new PrivateKey(""));
        MsgTokenMappingProposalValue proposal=new MsgTokenMappingProposalValue();
        proposal.setTitle("test");
        proposal.setDescription("test");
        proposal.setDenom("ibc/34343");
        proposal.setContract("0x2Bd4AF0C1D0c2930fEE852D07bB9dE87D8C07044");
        Message message=msg.produceTokenMappingProposal(proposal,"okt","0.050000000000000000");
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
