package com.okexchain.msg.gov.wasm;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.gov.Content;
import com.okexchain.msg.gov.MsgSubmitProposalValue;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgClearAdminProposal extends MsgBase {

    public MsgClearAdminProposal(){
        setMsgType("okexchain/gov/MsgSubmitProposal");
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
