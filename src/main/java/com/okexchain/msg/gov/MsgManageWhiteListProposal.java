package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;

public class MsgManageWhiteListProposal extends MsgBase {

    public MsgManageWhiteListProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public static void main(String[] args) throws JsonProcessingException {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgManageWhiteListProposal msg = new MsgManageWhiteListProposal();
        msg.init(key);

        Message messages = msg.produceManageWhiteListProposalMsg(
                "add to manage white list",
                "add to manage white list",
                "turing_pool",
                true,
                "100.00000000"
        );

        JSONObject res = msg.submit(messages, "0.05000000", "500000", "OKExChain delete token pair!");

        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Message produceManageWhiteListProposalMsg(
            String title,
            String description,
            String poolName,
            boolean isAdded,
            String amountDeposit
    ) {

        // proposal
        MsgManageWhiteListProposalValue proposal = new MsgManageWhiteListProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setPoolName(poolName);
        proposal.setIsAdded(isAdded);

        // content
        Content<MsgManageWhiteListProposalValue> content = new Content<>();
        content.setType("okexchain/farm/ManageWhiteListProposal");
        content.setValue(proposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(EnvInstance.getEnv().GetDenom());
        deposit.setAmount(Utils.NewDecString(amountDeposit));
        depositList.add(deposit);

        MsgSubmitProposalValue<Content<MsgManageWhiteListProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgManageWhiteListProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
