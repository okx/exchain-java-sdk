package com.okbchain.msg.gov.wasm;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.msg.gov.Content;
import com.okbchain.msg.gov.MsgSubmitProposalValue;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgClearAdminProposal extends MsgBase {

    public MsgClearAdminProposal(){
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgClearAdminProposal(MsgClearAdminProposalValue proposalValue,String denom, String amountDeposit){
        Content<MsgClearAdminProposalValue> content=new Content<>();
        content.setType("wasm/ClearAdminProposal");
        content.setValue(proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgClearAdminProposalValue>> value=new MsgSubmitProposalValue<>();
        value.setProposer(this.address);
        value.setInitialDeposit(tokenList);
        value.setContent(content);

        Message<MsgSubmitProposalValue<Content<MsgClearAdminProposalValue>>> msg=new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
