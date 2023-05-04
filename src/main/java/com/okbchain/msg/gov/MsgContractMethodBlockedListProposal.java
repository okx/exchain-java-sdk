package com.okbchain.msg.gov;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Contract_addresses;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;


import java.util.ArrayList;
import java.util.List;


public class MsgContractMethodBlockedListProposal extends MsgBase {

    public MsgContractMethodBlockedListProposal(){
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }


    public Message produceContractMethodBlockedListProposal(String title,
                                                            String description,
                                                            List<Contract_addresses> contract_addresses_list,
                                                            boolean isAdded,
                                                            String denom,
                                                            String amountDeposit){
        // proposal
        MsgContractMethodBlockedListProposalValue proposal = new MsgContractMethodBlockedListProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setContract_addresses(contract_addresses_list);
        proposal.setAdded(isAdded);

        return produceContractMethodBlockedListProposal(proposal,denom, amountDeposit);
    }


    public Message produceContractMethodBlockedListProposal(MsgContractMethodBlockedListProposalValue proposal,
                                                            String denom,
                                                            String amountDeposit){
        // content
        Content<MsgContractMethodBlockedListProposalValue> content = new Content<>();
        content.setType("okbchain/evm/ManageContractMethodBlockedListProposal");
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
