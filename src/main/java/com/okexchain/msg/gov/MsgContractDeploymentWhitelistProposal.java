package com.okexchain.msg.gov;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgContractDeploymentWhitelistProposal extends MsgBase {

    public MsgContractDeploymentWhitelistProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }


    public Message produceContractDeploymentWhitelistProposal(
            String title,
            String description,
            String[] distributorAddresses,
            boolean isAdded,
            String amountDeposit
    ) {

        // proposal
        MsgContractDeploymentWhitelistProposalValue proposal = new MsgContractDeploymentWhitelistProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setDistributorAddresses(distributorAddresses);
        proposal.setIsAdded(isAdded);

        return produceContractDeploymentWhitelistProposal(proposal, amountDeposit);
    }


    public Message produceContractDeploymentWhitelistProposal(
            MsgContractDeploymentWhitelistProposalValue proposal,
            String amountDeposit
    ) {

        // content
        Content<MsgContractDeploymentWhitelistProposalValue> content = new Content<>();
        content.setType("okexchain/evm/ManageContractDeploymentWhitelistProposal");
        content.setValue(proposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(EnvInstance.getEnv().GetDenom());
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
