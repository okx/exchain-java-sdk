package com.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.gov.wasm.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;



@Slf4j
public class TestWasmProposal {

    @Test
    public void testProduceMsgMigrateContractProposal() throws Exception {
        EnvInstance.getEnvLocalNet();

        MsgMigrateContractProposal msg = new MsgMigrateContractProposal();
        msg.initMnemonic("");
        String strJson = "{\"beneficiary\":\"ex1fsfwwvl93qv6r56jpu084hxxzn9zphnyxhske5\",\"verifier\":\"ex1qj5c07sm6jetjz8f509qtrxgh4psxkv3ddyq7u\"}";

        MsgMigrateContractProposalValue proposalValue = new MsgMigrateContractProposalValue();
        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setContract("ex18cszlvm6pze0x9sz32qnjq4vtd45xehqs8dq7cwy8yhq35wfnn3q3m0jje");
        proposalValue.setCodeId(4);
        proposalValue.setMsg(strJson);

        Message message = msg.produceMsgMigrateContractProposal(
                proposalValue,
                "okt",
                "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        System.out.println(res.toString());
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testProduceMsgPinCodesProposal() throws Exception {
        EnvInstance.getEnvLocalNet();

        MsgPinCodesProposal msg = new MsgPinCodesProposal();
        msg.initMnemonic("");
        MsgPinCodesProposalValue proposalValue = new MsgPinCodesProposalValue();
        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setCodeIds(new int[]{4, 5, 6});

        Message message = msg.produceMsgPinCodesProposal(proposalValue, "okt", "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testProduceMsgUnpinCodesProposal() throws Exception {
        EnvInstance.getEnvLocalNet();

        MsgUnpinCodesProposal msg=new MsgUnpinCodesProposal();
        msg.initMnemonic("");

        MsgUnpinCodesProposalValue proposalValue=new MsgUnpinCodesProposalValue();
        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setCodeIds(new int[]{4, 5, 6});

        Message message = msg.produceMsgUnpinCodesProposal(proposalValue, "okt", "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Test
    public void testProduceMsgUpdateAdminProposal(){
        EnvInstance.getEnvLocalNet();

        MsgUpdateAdminProposal msg=new MsgUpdateAdminProposal();
        msg.initMnemonic("");
        MsgUpdateAdminProposalValue proposalValue= new MsgUpdateAdminProposalValue();


        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setNewAdmin("0xEB3F2e59f7ed9E777Db64df4284f027c143Cbf66");
        proposalValue.setContract("ex18cszlvm6pze0x9sz32qnjq4vtd45xehqs8dq7cwy8yhq35wfnn3q3m0jje");

        Message message = msg.produceMsgUpdateAdminProposal(proposalValue, "okt", "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testProduceMsgClearAdminProposal(){
        EnvInstance.getEnvLocalNet();
        MsgClearAdminProposal msg=new MsgClearAdminProposal();
        msg.initMnemonic("");

        MsgClearAdminProposalValue proposalValue=new MsgClearAdminProposalValue();
        proposalValue.setTitle("ClearAdminProposal");
        proposalValue.setDescription("ClearAdminProposal");
        proposalValue.setContract("ex18cszlvm6pze0x9sz32qnjq4vtd45xehqs8dq7cwy8yhq35wfnn3q3m0jje");


        Message message = msg.produceMsgClearAdminProposal(proposalValue, "okt", "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testProduceMsgUpdateDeploymentWhitelistProposal(){
        EnvInstance.getEnvTestNet();
        MsgUpdateDeploymentWhitelistProposal msg=new MsgUpdateDeploymentWhitelistProposal();
        msg.initMnemonic("");

        MsgUpdateDeploymentWhitelistProposalValue proposalValue=new MsgUpdateDeploymentWhitelistProposalValue();

        proposalValue.setTitle("UpdateDeploymentWhitelistProposal");
        proposalValue.setDescription("UpdateDeploymentWhitelistProposal");
        String [] contractAddr={"all"};
        proposalValue.setDistributorAddrs(contractAddr);

        Message message = msg.produceMsgUpdateDeploymentWhitelistProposal(proposalValue, "okt", "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testProduceMsgUpdateWASMContractMethodBlockedListProposal(){
        String strJson="{\"contractAddr\":\"ex14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9s6fqu27\",\"methods\":[{\"name\":\"transfer\"}]}";
        EnvInstance.getEnvLocalNet();
        MsgUpdateWASMContractMethodBlockedListProposal msg=new MsgUpdateWASMContractMethodBlockedListProposal();

        msg.initMnemonic("");

        MsgUpdateWASMContractMethodBlockedListProposalValue proposalValue=new MsgUpdateWASMContractMethodBlockedListProposalValue();
        proposalValue.setTitle("UpdateWASMContractMethodBlockedListProposal");
        proposalValue.setDescription("UpdateWASMContractMethodBlockedListProposal");
        BlockedMethod blockedMethod=JSONObject.parseObject(strJson,BlockedMethod.class);

        proposalValue.setBlockedMethod(blockedMethod);
        proposalValue.setDelete(false);


        Message message = msg.produceMsgUpdateWASMContractMethodBlockedListProposal(proposalValue, "okt", "10");
        JSONObject res = msg.submit(message, "0.05000000", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testExtraProposal() throws Exception {
        EnvInstance.getEnvLocalNet();

        MsgExtraProposal.ProposalValue extraProposalValue = new MsgExtraProposal.ProposalValue();
        extraProposalValue.setTitle("modify wasm gas factor");
        extraProposalValue.setDescription("modify wasm gas factor");
        extraProposalValue.setAction("GasFactor");
        extraProposalValue.setExtra("{\"factor\":\"19.7\"}");

        MsgExtraProposal proposal = new MsgExtraProposal(extraProposalValue);
        proposal.initMnemonic("");

        Message message = proposal.buildMessage("okt","10");
        JSONObject res = proposal.submit(message, "0.05000000", "500000", "");
        System.out.println(res.toString());
        boolean succeed = proposal.isTxSucceed(res);
        assert succeed;
    }
}
