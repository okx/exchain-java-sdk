package com.okbchain.msg.gov;

import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgChangeDistributionTypeProposal extends MsgVote{

    public MsgChangeDistributionTypeProposal(){
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgChangeDistributionTypeProposal(MsgChangeDistributionTypeProposalValue proposalValue,String denom, String amountDeposit){

        Content<MsgChangeDistributionTypeProposalValue> content=new Content();
        content.setType("okbchain/distribution/ChangeDistributionTypeProposal");
        content.setValue(proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgChangeDistributionTypeProposalValue>> value = new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);


        Message<MsgSubmitProposalValue<Content<MsgChangeDistributionTypeProposalValue>>> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
