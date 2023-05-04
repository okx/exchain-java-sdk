package com.okbchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.utils.Utils;
import com.okbchain.utils.crypto.PrivateKey;

public class MsgMigrateContract extends MsgBase {

    public MsgMigrateContract() {
        setMsgType("wasm/MsgMigrateContract");
    }

    public Message produceMsg(String codeId, String contract, String msgStr) {
        MsgMigrateContractValue value = new MsgMigrateContractValue();

        value.setCodeId(codeId);
        value.setContract(contract);
        value.setSender(this.address);
        value.setMsg(Utils.getSortJson(msgStr));

        Message<MsgMigrateContractValue> msg = new Message<>();
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


        MsgMigrateContract msg = new MsgMigrateContract();
        msg.init(key);

        String msgStr = "{\"payout\":\"ex1qj5c07sm6jetjz8f509qtrxgh4psxkv3ddyq7u\"}";

        Message message = msg.produceMsg("3", "ex1yw4xvtc43me9scqfr2jr2gzvcxd3a9y4eq7gaukreugw2yd2f8tsfem2z7", msgStr);

        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}