package com.okbchain.msg.gov.wasm;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.msg.gov.Content;
import com.okbchain.msg.gov.MsgSubmitProposalValue;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgUpdateDeploymentWhitelistProposal extends MsgBase {

    public MsgUpdateDeploymentWhitelistProposal(){
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgUpdateDeploymentWhitelistProposal(MsgUpdateDeploymentWhitelistProposalValue proposalValue,String denom, String amountDeposit){
        Content<MsgUpdateDeploymentWhitelistProposalValue> content=new Content<>();
        content.setValue(proposalValue);
        content.setType("wasm/UpdateDeploymentWhitelistProposal");

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgUpdateDeploymentWhitelistProposalValue>> value=new MsgSubmitProposalValue<>();
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);
        value.setContent(content);

        Message<MsgSubmitProposalValue<Content<MsgUpdateDeploymentWhitelistProposalValue>>> msg=new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
