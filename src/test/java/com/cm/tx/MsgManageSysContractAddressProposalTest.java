package com.cm.tx;


import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.MsgManageSysContractAddressProposal;
import com.okexchain.msg.gov.MsgManageSysContractAddressProposalValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgManageSysContractAddressProposalTest {

    @Test
    public void testProduceMsgManageSysContractAddressProposal(){
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        EnvInstance.getEnv().setRestPathPrefix("/exchain/v1");
        EnvInstance.getEnv().setTxUrlPath("/exchain/v1/txs");
        EnvInstance.getEnv().setChainID("exchain-67");
        MsgManageSysContractAddressProposal msg=new MsgManageSysContractAddressProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        MsgManageSysContractAddressProposalValue value=new MsgManageSysContractAddressProposalValue();
        value.setTitle("test");
        value.setDescription("test");
        value.setContractAddr("ex1qhepxscl0s6vky43wlrccctek78ph28zwfy3hu");
        value.setAdded(true);

        Message message=msg.produceMsgManageSysContractAddressProposal(value,"okt","100.000000000000000000");

        JSONObject res = msg.submit(message, "0.05", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
