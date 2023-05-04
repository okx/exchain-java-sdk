package com.okbchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Message;
import com.okbchain.utils.Utils;
import com.okbchain.utils.crypto.PrivateKey;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
public class WasmTest {

    public static void main(String[] args) throws IOException {
        WasmTest wasmTest = new WasmTest();
        String privateKey = "8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17";

        log.info("=========================store hackatom wasm=============================");
        int code = wasmTest.storeCode("src/main/java/com/okbchain/msg/wasm/hackatom.wasm", privateKey);
        log.info("code id " + code);

        log.info("=========================instantiate hackatom =============================");
        String initMsg = """
                {"verifier": "0xbbE4733d85bc2b90682147779DA49caB38C0aA1F", "beneficiary": "0xbbE4733d85bc2b90682147779DA49caB38C0aA1F"}
                """;
        String contractAddress = wasmTest.instantiate(privateKey, code, initMsg);
        log.info("contractAddress " + contractAddress);

        log.info("==========================execute hackatom================================");
        String execMsg = """
                {"release":{}}
                """;
        wasmTest.executeContract(privateKey,contractAddress,execMsg);
        log.info("ok");
        log.info("============================update admin =================================");
        wasmTest.updateContractAdmin(privateKey,contractAddress,"0xbbE4733d85bc2b90682147779DA49caB38C0aA1F");
        log.info("ok");

        log.info("============================store burner wasm=============================");
        code = wasmTest.storeCode("src/main/java/com/okbchain/msg/wasm/burner.wasm", privateKey);
        log.info("code id " + code);
        log.info("============================migrate =================================");
        String migrateMsg = """
                {"payout": "0xbbE4733d85bc2b90682147779DA49caB38C0aA1F"}
                """;
        wasmTest.migrate(privateKey,contractAddress,code+"", migrateMsg);
        log.info("ok");
        log.info("============================clear admin ============================");
        wasmTest.clearAdmin(privateKey,contractAddress);
        log.info("ok");

    }


    private int storeCode(String wasmFile, String privateKey) throws IOException {
        byte[] byteArray = Utils.compressBytes(wasmFile);
        EnvInstance.getEnvLocalNet();

        MsgStoreCode msg = new MsgStoreCode();
        PrivateKey key = new PrivateKey(privateKey);
        msg.init(key);
        String jsonStr = "{\"permission\":\"Everybody\"}";
        Message message = msg.produceMsg(jsonStr, byteArray);
        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        try {
            if (!msg.isTxSucceed(res)) {
                throw new RuntimeException(res.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        String log = (String) res.get("raw_log");
        int index = log.lastIndexOf(":");
        log = log.substring(index);
        log = log.substring(2, log.indexOf("}") - 1);
        return Integer.valueOf(log);
    }

    private String instantiate(String privateKey, int codeId, String initMsg) {
        MsgInstantiateContract msg = new MsgInstantiateContract();
        msg.init(new PrivateKey(privateKey));
        List<Fund> fundArrayList = new ArrayList<>();
        Fund funds = new Fund();
        funds.setDenom("okb");
        funds.setAmount("1");
        fundArrayList.add(funds);
        Message message = msg.produceMsg("ex1h0j8x0v9hs4eq6ppgamemfyu4vuvp2sl0q9p3v", codeId + "", initMsg, fundArrayList, "v1.0.0");

        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        try {
            if (!msg.isTxSucceed(res)) {
                throw new RuntimeException(res.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        String log = (String) res.get("raw_log");
        int index = log.indexOf("address");
        return log.substring(index + 18, index + 60);
    }


    public void executeContract(String privateKey, String contractAddress, String execMsg) {
        EnvInstance.getEnvLocalNet();

        PrivateKey key = new PrivateKey(privateKey);
        MsgExecuteContract msg = new MsgExecuteContract();
        msg.init(key);

        List<Fund> fundsArrayList = new ArrayList<>();
        Fund fund = new Fund();
        fund.setDenom("okb");
        fund.setAmount("1");
        fundsArrayList.add(fund);

        Message message = msg.produceMsg(contractAddress, fundsArrayList, execMsg);

        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        try {
            if (!msg.isTxSucceed(res)){
                throw new RuntimeException(res.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void updateContractAdmin(String privateKey, String contractAddr, String newAdmin) {
        EnvInstance.getEnvLocalNet();
        MsgUpdateAdmin msg=new MsgUpdateAdmin();
        PrivateKey key = new PrivateKey(privateKey);
        msg.init(key);
        Message message= msg.produceMsg(newAdmin,contractAddr);
        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        try {
            if(!msg.isTxSucceed(res)){
                throw new RuntimeException(res.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void migrate(String privateKey, String contractAddr, String codeId, String migrateMsg) {
        PrivateKey key = new PrivateKey(privateKey);


        MsgMigrateContract msg = new MsgMigrateContract();
        msg.init(key);
        Message message = msg.produceMsg(codeId, contractAddr, migrateMsg);

        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        try {
            if (!msg.isTxSucceed(res)){
                throw new RuntimeException(res.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void clearAdmin(String privateKey, String contractAddr) {
        MsgClearAdmin msg=new MsgClearAdmin();
        PrivateKey key = new PrivateKey(privateKey);
        msg.init(key);
        Message message= msg.produceMsg(contractAddr);
        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        try {
            if (!msg.isTxSucceed(res)) {
                throw new RuntimeException(res.toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
