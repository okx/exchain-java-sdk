package com.okexchain.msg.gov.wasm;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.gov.Content;
import com.okexchain.msg.gov.MsgSubmitProposalValue;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgUpdateWASMContractMethodBlockedListProposal extends MsgBase {

    public MsgUpdateWASMContractMethodBlockedListProposal(){
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgUpdateWASMContractMethodBlockedListProposal(MsgUpdateWASMContractMethodBlockedListProposalValue proposalValue,String denom, String amountDeposit){
        Content<MsgUpdateWASMContractMethodBlockedListProposalValue> content=new Content<>();
        content.setValue(proposalValue);
        content.setType("wasm/UpdateWASMContractMethodBlockedListProposal");

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgUpdateWASMContractMethodBlockedListProposalValue>> value=new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setProposer(this.address);
        value.setInitialDeposit(tokenList);

        Message<MsgSubmitProposalValue<Content<MsgUpdateWASMContractMethodBlockedListProposalValue>>> msg=new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }
}
