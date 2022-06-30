package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgTokenMappingProposal extends MsgBase {
    public MsgTokenMappingProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public Message produceTokenMappingProposal(String title,String description,String denomIBC,String contract,String denom, String amountDeposit){
        MsgTokenMappingProposalValue proposalValue=new MsgTokenMappingProposalValue(title,description,denomIBC,contract);
        return produceTokenMappingProposal(proposalValue,denom,amountDeposit);
    }


    public Message produceTokenMappingProposal(MsgTokenMappingProposalValue proposal,String denom, String amountDeposit){

        // content
        Content<MsgTokenMappingProposalValue> content = new Content<>();
        content.setType("okexchain/erc20/TokenMappingProposal");
        content.setValue(proposal);

        // submit
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgTokenMappingProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgTokenMappingProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
