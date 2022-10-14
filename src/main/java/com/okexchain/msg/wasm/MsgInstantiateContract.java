package com.okexchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;

public class MsgInstantiateContract extends MsgBase {

    public MsgInstantiateContract() {
        setMsgType("wasm/MsgInstantiateContract");
    }

    public Message produceMsg(String admin, String codeId, String msg, List<Funds> funds, String label) {
        MsgInstantiateContractValue value = new MsgInstantiateContractValue();
        value.setAdmin(admin);
        value.setCodeId(codeId);
        value.setFunds(funds);
        value.setSender(this.address);
        value.setLabel(label);
        value.setMsg(Utils.getSortJson(msg));
        Message<MsgInstantiateContractValue> msge = new Message<>();
        msge.setType(msgType);
        msge.setValue(value);
        return msge;
    }


    //Query commands after initialization
    //http://localhost:8545/exchain/v1/txs/BBB6CC87E93C8A3580B0A94B87515B8411E60DDA7747601453F14B9F51B81224
    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("exchain-67");
        EnvInstance.getEnv().setRestServerUrl("http://127.0.0.1:8545");
        EnvInstance.getEnv().setRestPathPrefix("/exchain/v1");
        EnvInstance.getEnv().setTxUrlPath("/exchain/v1/txs");
        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");

        MsgInstantiateContract msg = new MsgInstantiateContract();
        msg.init(key);
        List<Funds> fundsArrayList = new ArrayList<>();
        Funds funds = new Funds();
        funds.setDenom("okt");
        funds.setAmount("1");
        fundsArrayList.add(funds);
        String msgStr = "{\"beneficiary\":\"ex1fsfwwvl93qv6r56jpu084hxxzn9zphnyxhske5\",\"verifier\":\"ex1qj5c07sm6jetjz8f509qtrxgh4psxkv3ddyq7u\"}";
        Message message = msg.produceMsg("ex1h0j8x0v9hs4eq6ppgamemfyu4vuvp2sl0q9p3v", "4", msgStr, fundsArrayList, "v1.0.0");

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