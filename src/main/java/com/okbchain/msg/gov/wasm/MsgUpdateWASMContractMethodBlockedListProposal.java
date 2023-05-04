package com.okbchain.msg.gov.wasm;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.msg.gov.Content;
import com.okbchain.msg.gov.MsgSubmitProposalValue;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgUpdateWASMContractMethodBlockedListProposal extends MsgBase {

    public MsgUpdateWASMContractMethodBlockedListProposal(){
        setMsgType("okbchain/gov/MsgSubmitProposal");
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
