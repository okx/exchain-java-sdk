package com.okbchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvBase;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import org.junit.Test;

public class MsgManageContractBytecodeProposalTest{
    @Test
    public void testProposal() {
        EnvBase env = EnvInstance.getEnvLocalNet();

        MsgManageContractBytecodeProposal msg = new MsgManageContractBytecodeProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        try {
            Message message = msg.produceMsgManageContractBytecodeProposal(
                    "update contract bytecode",
                    "update contract bytecode",
                    "0xFc0b06f1C1e82eFAdC0E5c226616B092D2cb97fF",
                    "0x2594E83A94F89Ffb923773ddDfF723BbE017b80D",
                    "okb",
                    "10");
            JSONObject res = msg.submit(message, "0.05000000", "500000", "");
            System.out.println(res.toString());
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}