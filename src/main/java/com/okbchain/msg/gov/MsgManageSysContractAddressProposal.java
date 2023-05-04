package com.okbchain.msg.gov;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgManageSysContractAddressProposal extends MsgBase {

    public MsgManageSysContractAddressProposal(){
        setMsgType("okbchain/gov/MsgSubmitProposal");
    }

    //produce MsgManageSysContractAddressProposal
    public Message produceMsgManageSysContractAddressProposal(MsgManageSysContractAddressProposalValue proposalValue,String denom, String amountDeposit){
        Content<MsgManageSysContractAddressProposalValue> content=new Content<>();
        content.setType("okbchain/evm/ManageSysContractAddressProposal");
        content.setValue(proposalValue);


        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgManageSysContractAddressProposalValue>> value = new MsgSubmitProposalValue();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgManageSysContractAddressProposalValue>>> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
