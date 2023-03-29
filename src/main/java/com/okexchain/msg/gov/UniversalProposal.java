package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UniversalProposal<T> extends MsgBase {
    private String proposalType;
    private T proposalValue;

    public UniversalProposal(String proposalType, T proposalValue) {
        this.proposalType = proposalType;
        this.proposalValue = proposalValue;
        this.setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public Message buildMessage(String denom, String amount) {
        // build content
        Content<T> content = new Content();
        content.setType(this.proposalType);
        content.setValue(this.proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amount));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<T>> value = new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);


        Message<MsgSubmitProposalValue<Content<T>>> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
