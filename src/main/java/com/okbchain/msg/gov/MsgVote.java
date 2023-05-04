package com.okbchain.msg.gov;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;

public class MsgVote extends MsgBase {

    public MsgVote() {
        setMsgType("okbchain/gov/MsgVote");
    }

    public static void main(String[] args) {
        MsgVote msg = new MsgVote();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceVoteMsg(
                "2",
                "Yes");

        msg.submit(messages, "0.01000000", "200000", "okbchain gov vot!");
    }

    public Message produceVoteMsg(String ProposalID, String option) {

        MsgVoteValue value = new MsgVoteValue();
        value.setProposalID(ProposalID);
        value.setVoters(this.address);
        value.setOption(option);

        Message<MsgVoteValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
