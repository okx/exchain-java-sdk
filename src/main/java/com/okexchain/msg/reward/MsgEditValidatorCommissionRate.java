package com.okexchain.msg.reward;


import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;



public class MsgEditValidatorCommissionRate extends MsgBase {

    public MsgEditValidatorCommissionRate() {
        setMsgType("okexchain/staking/MsgEditValidatorCommissionRate");
    }

    public Message produceMsg(String validatorAddress, float commissionRate) throws Exception {
        MsgEditValidatorCommissionRateValue value = new MsgEditValidatorCommissionRateValue();
        value.setValidatorAddress(validatorAddress);
        value.setCommissionRate(commissionRate);
        Message<MsgEditValidatorCommissionRateValue> msg = new Message<>();
        msg.setValue(value);
        msg.setType(msgType);
        return msg;
    }

    public static void main(String[] args) throws Exception {
        EnvInstance.getEnvTestNet();
        MsgEditValidatorCommissionRate msg = new MsgEditValidatorCommissionRate();
        msg.initMnemonic("local cram lens mushroom trade chalk kangaroo denial core exhaust ladder gesture");
        String bech32 = "exvaloper1l0ukprlxa8a7uh06jwkyvc28p6qz3qnd78slyk";
        Message message = msg.produceMsg(bech32, 0.2f);

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
