package com.okbchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.utils.Utils;
import com.okbchain.utils.crypto.PrivateKey;

import java.io.*;
import java.util.Base64;


public class MsgStoreCode extends MsgBase {
    public MsgStoreCode() {
        setMsgType("wasm/MsgStoreCode");
    }

    public Message produceMsg(String instantiatePermission, byte[] byteArray) throws UnsupportedEncodingException {
        //wasmByteCode Base64 encoding
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedText = encoder.encodeToString(byteArray);
        MsgStoreCodeValue value = new MsgStoreCodeValue();
        value.setInstantiatePermission(Utils.getSortJson(instantiatePermission));
        value.setSender(this.address);
        value.setWasmByteCode(encodedText);
        Message<MsgStoreCodeValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) throws IOException {
        byte[] byteArray = Utils.compressBytes("src/main/java/com/okbchain/msg/wasm/cw20_base.wasm");
        EnvInstance.getEnvLocalNet();

        MsgStoreCode msg = new MsgStoreCode();
        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");
        msg.init(key);
        String jsonStr = "{\"permission\":\"Everybody\"}";
        Message message = msg.produceMsg(jsonStr, byteArray);
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
