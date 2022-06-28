package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgContractTemplateProposal extends MsgBase {

    public MsgContractTemplateProposal(){
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public Message produceContractTemplateProposal(String title,String description,String contractType,String contract,String denom, String amountDeposit){
        MsgContractTemplateProposalValue proposal=new MsgContractTemplateProposalValue(title,description,contractType,contract);
        return produceContractTemplateProposal(proposal,denom,amountDeposit);

    }


    public Message produceContractTemplateProposal(MsgContractTemplateProposalValue proposal, String denom, String amountDeposit){

        //content
        Content<MsgContractTemplateProposalValue> content=new Content<>();
        content.setType("okexchain/erc20/ContractTemplateProposal");
        content.setValue(proposal);

        // submit
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgContractTemplateProposalValue>> value=new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setProposer(this.address);
        value.setInitialDeposit(tokenList);

        Message<MsgSubmitProposalValue<Content<MsgContractTemplateProposalValue>>> msg=new Message<>();

        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
