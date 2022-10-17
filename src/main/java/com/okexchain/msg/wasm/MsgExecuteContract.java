package com.okexchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;


public class MsgExecuteContract extends MsgBase {
    public MsgExecuteContract() {
        setMsgType("wasm/MsgExecuteContract");
    }

    public Message produceMsg(String contract, List<Fund> fundList, String msgStr) {
        MsgExecuteContractValue value = new MsgExecuteContractValue();

        value.setContract(contract);
        value.setFund(fundList);
        value.setMsg(Utils.getSortJson(msgStr));
        value.setFund(fundList);
        value.setSender(this.address);
        Message<MsgExecuteContractValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("exchain-67");
        EnvInstance.getEnv().setRestServerUrl("http://127.0.0.1:8545");
        EnvInstance.getEnv().setRestPathPrefix("/exchain/v1");
        EnvInstance.getEnv().setTxUrlPath("/exchain/v1/txs");

        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");
        MsgExecuteContract msg = new MsgExecuteContract();
        msg.init(key);

        List<Fund> fundsArrayList = new ArrayList<>();
        Fund fund = new Fund();
        fund.setDenom("okt");
        fund.setAmount("1");
        fundsArrayList.add(fund);
        String msgStr = "{\"release\":{}}";

        Message message = msg.produceMsg("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf", fundsArrayList, msgStr);

        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        System.out.println(res);
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
