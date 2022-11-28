package com.okexchain.msg.staking;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgAddShares extends MsgBase {

    public MsgAddShares() {
        setMsgType("okexchain/staking/MsgAddShares");
    }

    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        MsgAddShares msg = new MsgAddShares();
        msg.init(new PrivateKey("8d8322c9a4356247d9d603eff9e163c5b9166a6a7b92926d38aac813df60db5c"));
        //exvaloper1l0ukprlxa8a7uh06jwkyvc28p6qz3qnd78slyk
        //okexchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frshchly8
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
