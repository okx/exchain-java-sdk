package com.okexchain.msg.gov.wasm;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.gov.Content;
import com.okexchain.msg.gov.MsgSubmitProposalValue;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgUpdateDeploymentWhitelistProposal extends MsgBase {

    public MsgUpdateDeploymentWhitelistProposal(){
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgUpdateDeploymentWhitelistProposal(MsgUpdateDeploymentWhitelistProposalValue proposalValue,String denom, String amountDeposit){
        Content<MsgUpdateDeploymentWhitelistProposalValue> content=new Content<>();
        content.setValue(proposalValue);
        content.setType("wasm/ClearAdminProposal");

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
