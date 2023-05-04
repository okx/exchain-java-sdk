package com.okbchain.msg.gov;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgContractDeploymentWhitelistProposal extends MsgBase {

    public MsgContractDeploymentWhitelistProposal() {
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }


    public Message produceContractDeploymentWhitelistProposal(
            String title,
            String description,
            String[] distributorAddresses,
            boolean isAdded,
            String denom,
            String amountDeposit
    ) {

        // proposal
        MsgContractDeploymentWhitelistProposalValue proposal = new MsgContractDeploymentWhitelistProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setDistributorAddresses(distributorAddresses);
        proposal.setIsAdded(isAdded);

        return produceContractDeploymentWhitelistProposal(proposal,denom, amountDeposit);
    }


    public Message produceContractDeploymentWhitelistProposal(
            MsgContractDeploymentWhitelistProposalValue proposal,
            String denom,
            String amountDeposit
    ) {

        // content
        Content<MsgContractDeploymentWhitelistProposalValue> content = new Content<>();
        content.setType("okbchain/evm/ManageContractDeploymentWhitelistProposal");
        content.setValue(proposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(denom);
        deposit.setAmount(Utils.NewDecString(amountDeposit));
        depositList.add(deposit);

        MsgSubmitProposalValue<Content<MsgContractDeploymentWhitelistProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgContractDeploymentWhitelistProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
