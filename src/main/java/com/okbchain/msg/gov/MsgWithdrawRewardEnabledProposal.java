package com.okbchain.msg.gov;

import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgWithdrawRewardEnabledProposal extends MsgVote {

    public MsgWithdrawRewardEnabledProposal(){
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }


    public Message produceMsgWithdrawRewardEnabledProposal(MsgWithdrawRewardEnabledProposalValue proposalValue, String denom, String amountDeposit){
        Content<MsgWithdrawRewardEnabledProposalValue> content=new Content();
        content.setType("okbchain/distribution/WithdrawRewardEnabledProposal");
        content.setValue(proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgWithdrawRewardEnabledProposalValue>> value=new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);


        Message<MsgSubmitProposalValue<Content<MsgWithdrawRewardEnabledProposalValue>>> msg=new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
