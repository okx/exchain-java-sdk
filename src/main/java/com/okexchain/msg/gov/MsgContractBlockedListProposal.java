package com.okexchain.msg.gov;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgContractBlockedListProposal extends MsgBase {

    public MsgContractBlockedListProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }


    public Message produceContractDeploymentWhitelistProposal(
            String title,
            String description,
            String[] contractAddresses,
            boolean isAdded,
            String amountDeposit
    ) {

        // proposal
        MsgContractBlockedListProposalValue proposal = new MsgContractBlockedListProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setContractAddresses(contractAddresses);

        proposal.setIsAdded(isAdded);

        return produceContractDeploymentWhitelistProposal(proposal, amountDeposit);
    }


    public Message produceContractDeploymentWhitelistProposal(
            MsgContractBlockedListProposalValue proposal,
            String amountDeposit
    ) {

        // content
        Content<MsgContractBlockedListProposalValue> content = new Content<>();
        content.setType("okexchain/evm/ManageContractBlockedListProposal");
        content.setValue(proposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(EnvInstance.getEnv().GetDenom());
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
