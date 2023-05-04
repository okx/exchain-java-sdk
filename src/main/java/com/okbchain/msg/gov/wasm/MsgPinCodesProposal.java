package com.okbchain.msg.gov.wasm;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.msg.gov.Content;
import com.okbchain.msg.gov.MsgSubmitProposalValue;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgPinCodesProposal extends MsgBase {

    public MsgPinCodesProposal() {
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgPinCodesProposal(MsgPinCodesProposalValue proposalValue, String denom, String amountDeposit) {
        Content<MsgPinCodesProposalValue> content = new Content<>();
        content.setType("wasm/PinCodesProposal");
        content.setValue(proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgPinCodesProposalValue>> value=new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setProposer(this.address);
        value.setInitialDeposit(tokenList);

        Message<MsgSubmitProposalValue<Content<MsgPinCodesProposalValue>>> msg=new Message<>();
        msg.setType(msgType);
        msg.setValue(value);

        return msg;
    }
}
