package com.okexchain.msg.gov.wasm;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.gov.Content;
import com.okexchain.msg.gov.MsgSubmitProposalValue;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgMigrateContractProposal extends MsgBase {

    public MsgMigrateContractProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }


    public Message produceMsgMigrateContractProposal(MsgMigrateContractProposalValue proposalValue, String denom, String amountDeposit) {
        Content<MsgMigrateContractProposalValue> content = new Content<>();
        content.setType("wasm/MigrateContractProposal");
        content.setValue(proposalValue);
        // set token list
        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setDenom(denom);
        token.setAmount(Utils.NewDecString(amountDeposit));
        tokenList.add(token);

        MsgSubmitProposalValue<Content<MsgMigrateContractProposalValue>> value = new MsgSubmitProposalValue<>();
        value.setContent(content);
        value.setInitialDeposit(tokenList);
        value.setProposer(this.address);

        Message<MsgSubmitProposalValue<Content<MsgMigrateContractProposalValue>>> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);

        return msg;
    }
}
