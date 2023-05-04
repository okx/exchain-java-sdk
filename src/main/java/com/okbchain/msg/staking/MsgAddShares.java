package com.okbchain.msg.staking;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.utils.crypto.PrivateKey;

public class MsgAddShares extends MsgBase {

    public MsgAddShares() {
        setMsgType("okbchain/staking/MsgAddShares");
    }

    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        MsgAddShares msg = new MsgAddShares();
        msg.init(new PrivateKey(""));
        //exvaloper1l0ukprlxa8a7uh06jwkyvc28p6qz3qnd78slyk
        //okbchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frshchly8
        String[] validators = {"exvaloper1l0ukprlxa8a7uh06jwkyvc28p6qz3qnd78slyk"};
        Message messages = msg.produceMsg("ex1l5jugfjaqys4k64rpqud3lymf8a3csg6ds2j4h", validators);

        JSONObject res = msg.submit(messages, "0.09", "100000000", "");

        System.out.println(res);
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Message produceMsg(String delegator, String[] validators) {
        MsgAddSharesValue value = new MsgAddSharesValue();
        value.setDelAddr(delegator);
        value.setValAddrs(validators);

        Message<MsgAddSharesValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
