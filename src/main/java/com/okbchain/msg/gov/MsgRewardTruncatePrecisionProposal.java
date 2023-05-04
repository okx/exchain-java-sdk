package com.okbchain.msg.gov;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgRewardTruncatePrecisionProposal extends MsgBase {

    public MsgRewardTruncatePrecisionProposal() {
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }


    public Message produceMsgRewardTruncatePrecisionProposal(String title, String description, int precision, String denom, String amountDeposit) {
        MsgRewardTruncatePrecisionProposalValue proposalValue = new MsgRewardTruncatePrecisionProposalValue();
        proposalValue.setTitle(title);
        proposalValue.setDescription(description);
        proposalValue.setPrecision(String.valueOf(precision));
        return produceMsgRewardTruncatePrecisionProposal(proposalValue, denom, amountDeposit);
    }

    private Message produceMsgRewardTruncatePrecisionProposal(MsgRewardTruncatePrecisionProposalValue proposalValue, String denom, String amountDeposit) {

        Content<MsgRewardTruncatePrecisionProposalValue> content = new Content();
        content.setType("okbchain/distribution/RewardTruncatePrecisionProposal");
        content.setValue(proposalValue);


        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgRewardTruncatePrecisionProposalValue>> value = new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);


        Message<MsgSubmitProposalValue<Content<MsgRewardTruncatePrecisionProposalValue>>> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
