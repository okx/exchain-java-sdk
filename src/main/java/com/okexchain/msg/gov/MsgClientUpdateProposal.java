package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgClientUpdateProposal extends MsgBase {
    public MsgClientUpdateProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }


    public Message produceClientUpdateProposal(String title,String description,String subjectClientId,String substituteClientId,String denom, String amountDeposit) {
        MsgClientUpdateProposalValue proposal=new MsgClientUpdateProposalValue(title,description,subjectClientId,substituteClientId);
        return produceClientUpdateProposal(proposal,denom,amountDeposit);
    }

    public Message produceClientUpdateProposal(MsgClientUpdateProposalValue proposal, String denom, String amountDeposit) {

        Content<MsgClientUpdateProposalValue> content = new Content<>();
        content.setType("ibc.core.client.v1.ClientUpdateProposal");
        content.setValue(proposal);

        // submit
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgClientUpdateProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setProposer(this.address);
        value.setInitialDeposit(tokenList);

        Message<MsgSubmitProposalValue<Content<MsgClientUpdateProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
