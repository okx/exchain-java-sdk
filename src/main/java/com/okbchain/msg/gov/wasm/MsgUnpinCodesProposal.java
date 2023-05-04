package com.okbchain.msg.gov.wasm;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.msg.gov.Content;
import com.okbchain.msg.gov.MsgSubmitProposalValue;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgUnpinCodesProposal extends MsgBase {
    public MsgUnpinCodesProposal() {
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgUnpinCodesProposal(MsgUnpinCodesProposalValue proposalValue, String denom, String amountDeposit) {
        Content<MsgUnpinCodesProposalValue> content = new Content<>();
        content.setType("wasm/UnpinCodesProposal");
        content.setValue(proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgUnpinCodesProposalValue>> value=new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setProposer(this.address);
        value.setInitialDeposit(tokenList);

        Message<MsgSubmitProposalValue<Content<MsgUnpinCodesProposalValue>>> msg=new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }
}
