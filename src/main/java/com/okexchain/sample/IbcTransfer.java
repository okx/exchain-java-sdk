package com.okexchain.sample;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.TimeoutHeight;
import com.okexchain.msg.ibc.TransferMsg;

public class IbcTransfer {


    public static void main(String[] args) throws JsonProcessingException {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("exchain-101");
//        env.setTxUrlPath("/cosmos/tx/v1beta1/txs");
        env.setRestServerUrl("http://127.0.0.1:36659");
        env.setRestPathPrefix("/exchain/v1");
        env.setTxUrlPath("/exchain/v1/txs");


        testIbcTransfer();

    }


    static void testIbcTransfer() {
        TransferMsg msg = new TransferMsg();
        msg.initMnemonic("giggle sibling fun arrow elevator spoon blood grocery laugh tortoise culture tool");


        Message messages = msg.produceMsg("channel-0","2000000000000000000000", "cosmos1n064mg7jcxt2axur29mmek5ys7ghta4u4mhcjp", new TimeoutHeight("1","200000"));

        try {
            JSONObject result  = msg.submit(messages, "0.03", "2000000", "");
            System.out.println(result.toJSONString());
        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
