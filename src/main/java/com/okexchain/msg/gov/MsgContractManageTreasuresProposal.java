package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Contract_addresses;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.common.Treasures;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * MsgContractManageTreasuresProposal
 */
public class MsgContractManageTreasuresProposal extends MsgBase {

    public MsgContractManageTreasuresProposal(){
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }


    /***
     *
     * @param title
     * @param description
     * @param treasuresList
     * @param isAdded
     * @param denom
     * @param amountDeposit
     * @return
     */
    public Message produceContractManageTreasuresProposal(String title,
                                                          String description,
                                                          List<Treasures> treasuresList,
                                                          boolean isAdded,
                                                          String denom,
                                                          String amountDeposit){
        MsgContractManageTreasuresProposalValue proposal=new MsgContractManageTreasuresProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setTreasures(treasuresList);
        proposal.setAdded(isAdded);
        return produceContractManageTreasuresProposal(proposal,denom,amountDeposit);
    }

    /**
     *
     * @param proposal
     * @param denom
     * @param amountDeposit
     * @return
     */
    public Message produceContractManageTreasuresProposal(MsgContractManageTreasuresProposalValue proposal,
                                                          String denom,
                                                          String amountDeposit){
        //content object
        Content<MsgContractManageTreasuresProposalValue> content = new Content<>();
        content.setType("okexchain/mint/ManageTreasuresProposal");
        content.setValue(proposal);

        //initial_deposit object
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(denom);
        deposit.setAmount(Utils.NewDecString(amountDeposit));
        depositList.add(deposit);

        //MsgSubmitProposalValue object
        MsgSubmitProposalValue<Content<MsgContractManageTreasuresProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);


        Message<MsgSubmitProposalValue<Content<MsgContractManageTreasuresProposalValue>>> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
