package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.ContractAddresses;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;


import java.util.ArrayList;
import java.util.List;


public class MsgContractMethodBlockedListProposal extends MsgBase {

    public MsgContractMethodBlockedListProposal(){
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }


    public Message produceContractMethodBlockedListProposal(String title,
                                                            String description,
                                                            List<ContractAddresses> contractAddressesList,
                                                            boolean isAdded,
                                                            String denom,
                                                            String amountDeposit){
        // proposal
        MsgContractMethodBlockedListProposalValue proposal = new MsgContractMethodBlockedListProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setContractAddresses(contractAddressesList);
        proposal.setAdded(isAdded);

        return produceContractMethodBlockedListProposal(proposal,denom, amountDeposit);
    }


    public Message produceContractMethodBlockedListProposal(MsgContractMethodBlockedListProposalValue proposal,
                                                            String denom,
                                                            String amountDeposit){
        // content
        Content<MsgContractMethodBlockedListProposalValue> content = new Content<>();
        content.setType("okexchain/evm/ManageContractMethodBlockedListProposal");
        content.setValue(proposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(denom);
        deposit.setAmount(Utils.NewDecString(amountDeposit));
        depositList.add(deposit);

        MsgSubmitProposalValue<Content<MsgContractMethodBlockedListProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgContractMethodBlockedListProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
