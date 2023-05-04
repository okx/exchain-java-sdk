package com.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.gov.wasm.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;



@Slf4j
public class TestWasmProposal {

    @Test
    public void testProduceMsgMigrateContractProposal() throws Exception {
        EnvInstance.getEnvLocalNet();

        MsgMigrateContractProposal msg = new MsgMigrateContractProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        String strJson = "{\"beneficiary\":\"ex1fsfwwvl93qv6r56jpu084hxxzn9zphnyxhske5\",\"verifier\":\"ex1qj5c07sm6jetjz8f509qtrxgh4psxkv3ddyq7u\"}";

        MsgMigrateContractProposalValue proposalValue = new MsgMigrateContractProposalValue();
        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setContract("0x07fA2f05F447DDEfd1B31FB5611C8d3252fF1D42");
        proposalValue.setCodeId(4);
        proposalValue.setMsg(strJson);

        Message message = msg.produceMsgMigrateContractProposal(
                proposalValue,
                "okb",
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
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        MsgPinCodesProposalValue proposalValue = new MsgPinCodesProposalValue();
        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setCodeIds(new int[]{4, 5, 6});

        Message message = msg.produceMsgPinCodesProposal(proposalValue, "okb", "10");
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
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        MsgUnpinCodesProposalValue proposalValue=new MsgUnpinCodesProposalValue();
        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setCodeIds(new int[]{4, 5, 6});

        Message message = msg.produceMsgUnpinCodesProposal(proposalValue, "okb", "10");
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
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        MsgUpdateAdminProposalValue proposalValue= new MsgUpdateAdminProposalValue();


        proposalValue.setTitle("test");
        proposalValue.setDescription("test");
        proposalValue.setNewAdmin("0xEB3F2e59f7ed9E777Db64df4284f027c143Cbf66");
        proposalValue.setContract("0x07fA2f05F447DDEfd1B31FB5611C8d3252fF1D42");

        Message message = msg.produceMsgUpdateAdminProposal(proposalValue, "okb", "10");
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
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        MsgClearAdminProposalValue proposalValue=new MsgClearAdminProposalValue();
        proposalValue.setTitle("ClearAdminProposal");
        proposalValue.setDescription("ClearAdminProposal");
        proposalValue.setContract("0x07fA2f05F447DDEfd1B31FB5611C8d3252fF1D42");


        Message message = msg.produceMsgClearAdminProposal(proposalValue, "okb", "10");
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
        EnvInstance.getEnvLocalNet();
        MsgUpdateDeploymentWhitelistProposal msg=new MsgUpdateDeploymentWhitelistProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        MsgUpdateDeploymentWhitelistProposalValue proposalValue=new MsgUpdateDeploymentWhitelistProposalValue();

        proposalValue.setTitle("UpdateDeploymentWhitelistProposal");
        proposalValue.setDescription("UpdateDeploymentWhitelistProposal");
        String [] contractAddr={"0xEB3F2e59f7ed9E777Db64df4284f027c143Cbf66","0x66d351A5509dd876A01a8624B69721d845562e7D"};
        proposalValue.setDistributorAddrs(contractAddr);

        Message message = msg.produceMsgUpdateDeploymentWhitelistProposal(proposalValue, "okb", "10");
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
        String strJson="{\"contractAddr\":\"0x07fA2f05F447DDEfd1B31FB5611C8d3252fF1D42\",\"methods\":[{\"name\":\"transfer\"}]}";
        EnvInstance.getEnvLocalNet();
        MsgUpdateWASMContractMethodBlockedListProposal msg=new MsgUpdateWASMContractMethodBlockedListProposal();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        MsgUpdateWASMContractMethodBlockedListProposalValue proposalValue=new MsgUpdateWASMContractMethodBlockedListProposalValue();
        proposalValue.setTitle("UpdateWASMContractMethodBlockedListProposal");
        proposalValue.setDescription("UpdateWASMContractMethodBlockedListProposal");
        BlockedMethod blockedMethod=JSONObject.parseObject(strJson,BlockedMethod.class);

        proposalValue.setBlockedMethod(blockedMethod);
        proposalValue.setDelete(false);


        Message message = msg.produceMsgUpdateWASMContractMethodBlockedListProposal(proposalValue, "okb", "10");
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
        proposal.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message message = proposal.buildMessage("okb","10");
        JSONObject res = proposal.submit(message, "0.05000000", "500000", "");
        System.out.println(res.toString());
        boolean succeed = proposal.isTxSucceed(res);
        assert succeed;
    }
}
