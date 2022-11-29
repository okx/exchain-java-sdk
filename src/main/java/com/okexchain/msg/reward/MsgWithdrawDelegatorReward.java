package com.okexchain.msg.reward;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgWithdrawDelegatorReward extends MsgBase {
    public MsgWithdrawDelegatorReward() {
        setMsgType("okexchain/distribution/MsgWithdrawDelegatorReward");
    }

    public Message produceMsg(String delegatorAddress, String validatorAddress) {
        MsgWithdrawDelegatorRewardValue value = new MsgWithdrawDelegatorRewardValue();
        value.setValidatorAddress(validatorAddress);
        value.setDelegatorAddress(delegatorAddress);

        Message<MsgWithdrawDelegatorRewardValue> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);
        return msg;
    }
    //https://www.oklink.com/zh-cn/okc-test/tx/0xF0C237D2EA496D6BE13BBA591A65AA98DD186A534652EEF0D5B030E262089511
    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        MsgWithdrawDelegatorReward msg=new MsgWithdrawDelegatorReward();
        msg.init(new PrivateKey(""));
        Message message=msg.produceMsg("ex1l5jugfjaqys4k64rpqud3lymf8a3csg6ds2j4h","exvaloper1l0ukprlxa8a7uh06jwkyvc28p6qz3qnd78slyk");
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
