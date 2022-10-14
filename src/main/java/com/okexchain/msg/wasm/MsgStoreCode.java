package com.okexchain.msg.wasm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;
import static com.google.common.io.ByteStreams.toByteArray;

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
        value.setInstantiatePermission(JSON.parseObject(instantiatePermission));
        value.setSender(this.address);
        value.setWasmByteCode(encodedText);
        Message<MsgStoreCodeValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        byte[] byteArray = null;
        try {
            byteArray = MsgStoreCode.InputStream2ByteArray("/Users/asahi/IdeaProjects/exchain-java-sdk/src/main/java/com/okexchain/msg/wasm/burner.wasm");
        } catch (IOException e) {
            e.printStackTrace();
        }
        EnvInstance.getEnv().setChainID("exchain-67");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        EnvInstance.getEnv().setRestPathPrefix("/exchain/v1");

        MsgStoreCode msg = new MsgStoreCode();
        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");
        msg.init(key);
        String jsonStr = "{\"permission\":\"Everybody\"}";
        //String jsonStr = "{\"permission\":\"Nobody\"}";
        //String jsonStr = "{\"address\":\"ex1qj5c07sm6jetjz8f509qtrxgh4psxkv3ddyq7u\",\"permission\":\"OnlyAddress\"}";
        Message message = msg.produceMsg(jsonStr, byteArray);
        JSONObject res = msg.submit(message, "0.05", "500000", "");
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //read wasm file and change to byte array
    private static byte[] InputStream2ByteArray(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] byteArray = toByteArray(in);
        in.close();
        return byteArray;
    }
}
