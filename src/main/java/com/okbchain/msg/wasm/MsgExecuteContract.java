package com.okbchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.utils.Utils;
import com.okbchain.utils.crypto.PrivateKey;

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
        EnvInstance.getEnvLocalNet();

        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");
        MsgExecuteContract msg = new MsgExecuteContract();
        msg.init(key);

        List<Fund> fundsArrayList = new ArrayList<>();
        Fund fund = new Fund();
        fund.setDenom("okb");
        fund.setAmount("1");
        fundsArrayList.add(fund);
        String msgStr = "{\"release\":{}}";

        Message message = msg.produceMsg("0xf8fD315F3fD46Cb79B0870Bc61088A4939e8Ab96", fundsArrayList, msgStr);

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
