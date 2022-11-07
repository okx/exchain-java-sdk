package com.okexchain.msg.gov.wasm;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.gov.Content;
import com.okexchain.msg.gov.MsgSubmitProposalValue;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgUpdateAdminProposal extends MsgBase {

    public MsgUpdateAdminProposal(){
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public  Message produceMsgUpdateAdminProposal(MsgUpdateAdminProposalValue proposalValue,String denom, String amountDeposit){
        Content<MsgUpdateAdminProposalValue> content=new Content<>();
        content.setValue(proposalValue);
        content.setType("wasm/UpdateAdminProposal");

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgUpdateAdminProposalValue>>  value=new MsgSubmitProposalValue<>();
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);
        value.setContent(content);

        Message<MsgSubmitProposalValue<Content<MsgUpdateAdminProposalValue>>> msg=new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
