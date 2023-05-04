package com.cm.tx;


import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.gov.MsgManageSysContractAddressProposal;
import com.okbchain.msg.gov.MsgManageSysContractAddressProposalValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MsgManageSysContractAddressProposalTest {

    @Test
    public void testProduceMsgManageSysContractAddressProposal(){
        EnvInstance.getEnvLocalNet();
        MsgManageSysContractAddressProposal msg=new MsgManageSysContractAddressProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        MsgManageSysContractAddressProposalValue value=new MsgManageSysContractAddressProposalValue();
        value.setTitle("test");
        value.setDescription("test");
        value.setContractAddr("0x4C12e733e58819A1d3520f1E7aDCc614Ca20De64");
        value.setAdded(true);

        Message message=msg.produceMsgManageSysContractAddressProposal(value,"okb","100.000000000000000000");

        JSONObject res = msg.submit(message, "0.05", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
