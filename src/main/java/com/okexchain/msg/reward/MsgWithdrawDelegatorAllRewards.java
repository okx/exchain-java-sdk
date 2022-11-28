package com.okexchain.msg.reward;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgWithdrawDelegatorAllRewards extends MsgBase {
    public MsgWithdrawDelegatorAllRewards() {
        setMsgType("okexchain/distribution/MsgWithdrawDelegatorAllRewards");
    }

    public Message produceMsg(String delegatorAddress) {
        MsgWithdrawDelegatorAllRewardsValue value = new MsgWithdrawDelegatorAllRewardsValue();
        value.setDelegatorAddress(delegatorAddress);
        Message<MsgWithdrawDelegatorAllRewardsValue> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);
        return msg;
    }
    //ex1l0ukprlxa8a7uh06jwkyvc28p6qz3qndgq6mqv
    //exvaloper1l0ukprlxa8a7uh06jwkyvc28p6qz3qnd78slyk
    //https://www.oklink.com/zh-cn/okc-test/tx/0x02F6F57207B5EA1B644A5F85C7F2150EDBB5669EE6D79EA66F922E27195F4313
    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        MsgWithdrawDelegatorAllRewards msg = new MsgWithdrawDelegatorAllRewards();
        //msg.initMnemonic("local cram lens mushroom trade chalk kangaroo denial core exhaust ladder gesture");
        msg.init(new PrivateKey("8d8322c9a4356247d9d603eff9e163c5b9166a6a7b92926d38aac813df60db5c"));
        MsgWithdrawDelegatorAllRewardsValue value=new MsgWithdrawDelegatorAllRewardsValue();
        Message message=msg.produceMsg("ex1l5jugfjaqys4k64rpqud3lymf8a3csg6ds2j4h");
        JSONObject res = msg.submit(message, "0.09", "100000000", "");
        System.out.println(res);
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
