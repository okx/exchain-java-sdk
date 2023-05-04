package com.okbchain.msg.gov;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgContractBlockedListProposal extends MsgBase {

    public MsgContractBlockedListProposal() {
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }


    public Message produceContractDeploymentWhitelistProposal(
            String title,
            String description,
            String[] contractAddresses,
            boolean isAdded,
            String denom,
            String amountDeposit
    ) {

        // proposal
        MsgContractBlockedListProposalValue proposal = new MsgContractBlockedListProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setContractAddresses(contractAddresses);

        proposal.setIsAdded(isAdded);

        return produceContractDeploymentWhitelistProposal(proposal,denom, amountDeposit);
    }


    public Message produceContractDeploymentWhitelistProposal(
            MsgContractBlockedListProposalValue proposal,
            String denom,
            String amountDeposit
    ) {

        // content
        Content<MsgContractBlockedListProposalValue> content = new Content<>();
        content.setType("okbchain/evm/ManageContractBlockedListProposal");
        content.setValue(proposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(denom);
        deposit.setAmount(Utils.NewDecString(amountDeposit));
        depositList.add(deposit);

        MsgSubmitProposalValue<Content<MsgContractBlockedListProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgContractBlockedListProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
