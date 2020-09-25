package io.cosmos.msg;

import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgVoteValue;
import io.cosmos.types.Token;

import java.util.ArrayList;
import java.util.List;

public class MsgVote extends MsgBase {

    public MsgVote() {
        setMsgType("okexchain/gov/MsgVote");
    }

    public static void main(String[] args) {
        MsgVote msg = new MsgVote();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceVoteMsg(
                "2",
                "Yes");

        msg.submit(messages, "0.01000000", "200000", "okexchain gov vot!");
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
