package com.okexchain.msg.gov;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgManageContractBytecodeProposal extends MsgBase {

    public MsgManageContractBytecodeProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public Message produceMsgManageContractBytecodeProposal(String title, String description, String contractAddresses, String substituteContract, String denom, String amountDeposit) throws Exception {
        contractAddresses = Utils.convertHexAddrToExBech32(contractAddresses);
        substituteContract = Utils.convertHexAddrToExBech32(substituteContract);

        // proposal
        MsgManageContractBytecodeProposalValue proposal = new MsgManageContractBytecodeProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setContract(contractAddresses);
        proposal.setSubstituteContract(substituteContract);

        return produceMsgManageContractBytecodeProposal(proposal, denom, amountDeposit);
    }


    private Message produceMsgManageContractBytecodeProposal(MsgManageContractBytecodeProposalValue proposalValue, String denom, String amountDeposit) {

        // build content
        Content<MsgManageContractBytecodeProposalValue> content = new Content();
        content.setType("okexchain/evm/ManageContractBytecode");
        content.setValue(proposalValue);

        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgManageContractBytecodeProposalValue>> value = new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);


        Message<MsgSubmitProposalValue<Content<MsgManageContractBytecodeProposalValue>>> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
