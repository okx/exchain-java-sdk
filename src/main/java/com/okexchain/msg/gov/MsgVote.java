package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;

public class MsgVote extends MsgBase {

    public MsgVote() {
        setMsgType("okexchain/gov/MsgVote");
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchain-66");
        EnvInstance.getEnv().setRestServerUrl("http://okexchaintest.okexcn.com:26659");

        MsgVote msg = new MsgVote();
        msg.init("okexchain1wgtgkzyzm3ecxntpqzzs2vr4rhqawlnpdld09k", "");

        Message messages = msg.produceVoteMsg(
                "12",
                "Yes");

        UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.05000000", "500000", "");
        JSONObject jsonpObject = JSON.parseObject(unsignedTx.toString(), Feature.OrderedField);
        jsonpObject.put("addressIndex", 820);

        System.out.println(jsonpObject.toString());
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
