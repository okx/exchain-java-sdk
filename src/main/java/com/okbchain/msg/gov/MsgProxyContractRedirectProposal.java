package com.okbchain.msg.gov;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgProxyContractRedirectProposal extends MsgBase {

    public MsgProxyContractRedirectProposal() {
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }

    public Message produceProxyContractRedirectProposal(String title, String description, String denomIBC, String type, String addr, String denom, String amountDeposit) {
        MsgProxyContractRedirectProposalValue proposal = new MsgProxyContractRedirectProposalValue(title, description, denomIBC, type, addr);
        return produceProxyContractRedirectProposal(proposal, denom, amountDeposit);

    }

    public Message produceProxyContractRedirectProposal(MsgProxyContractRedirectProposalValue proposal, String denom, String amountDeposit) {
        Content<MsgProxyContractRedirectProposalValue> content = new Content<>();
        content.setType("okbchain/erc20/ProxyContractRedirectProposal");
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
