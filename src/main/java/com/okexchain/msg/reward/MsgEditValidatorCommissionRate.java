package com.okexchain.msg.reward;


import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

import java.math.BigDecimal;


public class MsgEditValidatorCommissionRate extends MsgBase {

    public MsgEditValidatorCommissionRate() {
        setMsgType("okexchain/staking/MsgEditValidatorCommissionRate");
    }

    public Message produceMsg(String validatorAddress, BigDecimal commissionRate) throws Exception {
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
        msg.initMnemonic("");
        String bech32 = "exvaloper1l0ukprlxa8a7uh06jwkyvc28p6qz3qnd78slyk";
        Message message = msg.produceMsg(bech32, BigDecimal.valueOf(0.5));

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
