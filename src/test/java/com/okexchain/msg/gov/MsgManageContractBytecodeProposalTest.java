package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.wasm.MsgMigrateContractProposal;
import com.okexchain.msg.gov.wasm.MsgMigrateContractProposalValue;
import org.junit.Test;

public class MsgManageContractBytecodeProposalTest{
    @Test
    public void testProposal() {
        EnvBase env = EnvInstance.getEnvLocalNet();

        MsgManageContractBytecodeProposal msg = new MsgManageContractBytecodeProposal();
        msg.initMnemonic("");

        try {
            Message message = msg.produceMsgManageContractBytecodeProposal(
                    "update contract bytecode",
                    "update contract bytecode",
                    "0xFc0b06f1C1e82eFAdC0E5c226616B092D2cb97fF",
                    "0x2594E83A94F89Ffb923773ddDfF723BbE017b80D",
                    "okt",
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