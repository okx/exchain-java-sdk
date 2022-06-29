package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgProxyContractRedirectProposal extends MsgBase {

    public MsgProxyContractRedirectProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public Message produceProxyContractRedirectProposal(String title, String description, String denomIBC, int type, String addr, String denom, String amountDeposit) {
        MsgProxyContractRedirectProposalValue proposal=new MsgProxyContractRedirectProposalValue(title,description,denomIBC,type,addr);
        return produceProxyContractRedirectProposal(proposal,denom,amountDeposit);

    }

    public Message produceProxyContractRedirectProposal(MsgProxyContractRedirectProposalValue proposal, String denom, String amountDeposit) {
        Content<MsgProxyContractRedirectProposalValue> content = new Content<>();
        content.setType("okexchain/erc20/ProxyContractRedirectProposal");
        content.setValue(proposal);

        // submit
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgProxyContractRedirectProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setProposer(this.address);
        value.setContent(content);
        value.setInitialDeposit(tokenList);

        Message<MsgSubmitProposalValue<Content<MsgProxyContractRedirectProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
