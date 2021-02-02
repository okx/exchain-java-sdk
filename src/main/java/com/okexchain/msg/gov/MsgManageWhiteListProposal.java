package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;

public class MsgManageWhiteListProposal extends MsgBase {

    public MsgManageWhiteListProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public static void main(String[] args) throws JsonProcessingException {
        EnvInstance.getEnv().setChainID("okexchain-66");
        EnvInstance.getEnv().setRestServerUrl("https://okex.com");

        MsgManageWhiteListProposal msg = new MsgManageWhiteListProposal();
        msg.init("okexchain1s6nfs7mlj7ewsskkrmekqhpq2w234fcz9sp3uz", "");

        Message messages = msg.produceManageWhiteListProposalMsg(
                "add ethk_okt to manage while list",
                "add ethk_okt to manage while list",
                "ethk_okt",
                true,
                "100.00000000"
        );

        UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.05000000", "500000", "");
        JSONObject jsonpObject = JSON.parseObject(unsignedTx.toString(), Feature.OrderedField);
        jsonpObject.put("addressIndex", 2);

        System.out.println(jsonpObject.toString());
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
