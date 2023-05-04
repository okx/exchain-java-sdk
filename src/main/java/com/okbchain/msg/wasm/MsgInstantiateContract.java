package com.okbchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.utils.Utils;
import com.okbchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;

public class MsgInstantiateContract extends MsgBase {

    public MsgInstantiateContract() {
        setMsgType("wasm/MsgInstantiateContract");
    }

    public Message produceMsg(String admin, String codeId, String msg, List<Fund> fund, String label) {
        MsgInstantiateContractValue value = new MsgInstantiateContractValue();
        value.setAdmin(admin);
        value.setCodeId(codeId);
        value.setFund(fund);
        value.setSender(this.address);
        value.setLabel(label);
        value.setMsg(Utils.getSortJson(msg));
        Message<MsgInstantiateContractValue> msge = new Message<>();
        msge.setType(msgType);
        msge.setValue(value);
        return msge;
    }


    public static void main(String[] args) {
        EnvInstance.getEnvLocalNet();
        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");

        MsgInstantiateContract msg = new MsgInstantiateContract();
        msg.init(key);
        List<Fund> fundArrayList = new ArrayList<>();
        Fund funds = new Fund();
        funds.setDenom("okb");
        funds.setAmount("1");
        fundArrayList.add(funds);
        //String msgStr = "{\"beneficiary\":\"ex1fsfwwvl93qv6r56jpu084hxxzn9zphnyxhske5\",\"verifier\":\"ex1qj5c07sm6jetjz8f509qtrxgh4psxkv3ddyq7u\"}";
        //String msgStr="{\"beneficiary\":\"ex1fsfwwvl93qv6r56jpu084hxxzn9zphnyxhske5\",\"verifier\":\"ex1qj5c07sm6jetjz8f509qtrxgh4psxkv3ddyq7u\",\"decimals\":10,\"initial_balances\":[{\"address\":\"ex1h0j8x0v9hs4eq6ppgamemfyu4vuvp2sl0q9p3v\",\"amount\":\"100000000\"}],\"name\":\"okt\", \"symbol\":\"okt\"}";
        String msgStr="{\"decimals\":10,\"initial_balances\":[{\"address\":\"0xbbE4733d85bc2b90682147779DA49caB38C0aA1F\",\"amount\":\"100000000\"}],\"name\":\"okt\", \"symbol\":\"okt\"}";
        Message message = msg.produceMsg("ex1h0j8x0v9hs4eq6ppgamemfyu4vuvp2sl0q9p3v", "2", msgStr, fundArrayList, "v1.0.0");

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
