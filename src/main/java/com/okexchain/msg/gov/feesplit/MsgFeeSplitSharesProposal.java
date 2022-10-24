package com.okexchain.msg.gov.feesplit;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.gov.Content;
import com.okexchain.msg.gov.MsgSubmitProposalValue;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgFeeSplitSharesProposal extends MsgBase {

    public MsgFeeSplitSharesProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }


    public Message produceMsgFeeSplitSharesProposal(String title, String description, List<Share> shareList, String denom, String amountDeposit) {
        MsgFeeSplitSharesProposalValue proposalValue = new MsgFeeSplitSharesProposalValue();
        proposalValue.setTitle(title);
        proposalValue.setDescription(description);
        proposalValue.setShareList(shareList);
        return produceMsgFeeSplitSharesProposal(proposalValue, denom, amountDeposit);
    }

    public Message produceMsgFeeSplitSharesProposal(MsgFeeSplitSharesProposalValue proposalValue, String denom, String amountDeposit) {
        Content<MsgFeeSplitSharesProposalValue> content = new Content();
        content.setType("okexchain/feesplit/SharesProposal");
        content.setValue(proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgFeeSplitSharesProposalValue>> value = new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgFeeSplitSharesProposalValue>>> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
