package com.okexchain.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgDeListProposalValue;
import com.okexchain.msg.types.MsgSubmitDeListProposalValue;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgDeListProposal extends MsgBase {
    public MsgDeListProposal() {
        setMsgType("okexchain/gov/MsgSubmitProposal");
    }

    public static void main(String[] args) throws JsonProcessingException {
        MsgDeListProposal msg = new MsgDeListProposal();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceDelistProposalMsg(
                "delete token pair proposal",
                "delete xxx-okt",
                "xxx",
                EnvInstance.getEnv().GetDenom(),
                "100.00000000"
        );

        msg.submit(messages, "0.01000000", "200000", "OKExChain delete token pair!");
    }

    public Message produceDelistProposalMsg(
            String title,
            String description,
            String baseAsset,
            String quoteAsset,
            String amountDeposit
    ) {

        // proposal
        MsgDeListProposalValue proposal = new MsgDeListProposalValue();
        proposal.setTitle(title);
        proposal.setDescription(description);
        proposal.setProposer(this.address);
        proposal.setBaseAsset(baseAsset);
        proposal.setQuoteAsset(quoteAsset);

        // wrapper
        Message<MsgDeListProposalValue> wrapperMsg = new Message<>();
        wrapperMsg.setType("okexchain/dex/DelistProposal");
        wrapperMsg.setValue(proposal);

        // submit
        List<Token> depositList = new ArrayList<>();
        Token deposit = new Token();
        deposit.setDenom(EnvInstance.getEnv().GetDenom());
        deposit.setAmount(Utils.NewDecString(amountDeposit));
        depositList.add(deposit);

        MsgSubmitDeListProposalValue value = new MsgSubmitDeListProposalValue();
        value.setContent(wrapperMsg);
        value.setInitialDeposit(depositList);
        value.setProposer(this.address);

        Message<MsgSubmitDeListProposalValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
