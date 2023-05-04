package com.okbchain.msg.wasm;


import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Result;
import com.okbchain.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;


import java.io.UnsupportedEncodingException;
import java.util.Base64;


/***
 * @author lixuqing
 * @date 2022-10-10
 * @WasmQuery
 */
@Slf4j
public class WasmQuery {


    private static final String QUERY_LIST_CODE = "/wasm/code";

    //%s=codeID
    private static final String QUERY_LIST_CONTRACTS = "/wasm/code/%s/contracts";

    //%s=codeID
    private static final String QUERY_CODE = "/wasm/code/%s";

    //%s=contractAddr
    private static final String QUERY_CONTRACT = "/wasm/contract/%s";

    //%s=contractAddr
    private static final String QUERY_CONTRACT_HISTORY = "/wasm/contract/%s/history";

    //%s=contractAddr
    private static final String QUERY_CONTRACT_STATE_All = "/wasm/contract/%s/state";

    //%s=contractAddr
    //%s=query
    private static final String QUERY_CONTRACT_STATE_SMART = "/wasm/contract/%s/smart/%s?encoding=base64";

    //%s=contractAddr
    //%s=key
    private static final String QUERY_CONTRACT_STATE_RAW = "/wasm/contract/%s/raw/%s?encoding=hex";

    /**
     * queryListCode
     *
     * @return
     */
    public static String queryListCode() {
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + QUERY_LIST_CODE);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }


    /**
     * @param codeID
     * @return
     */
    public static String queryListContractsByCodeID(String codeID) {
        String basePath = String.format(QUERY_LIST_CONTRACTS, codeID);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }

    /**
     * @param codeID
     * @return
     */
    public static String queryCodeByCodeID(String codeID) {
        String basePath = String.format(QUERY_CODE, codeID);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }


    /**
     * @param contractAddr
     * @return
     */
    public static String queryContractByContractAddr(String contractAddr) {
        String basePath = String.format(QUERY_CONTRACT, contractAddr);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }


    /**
     * @param contractAddr
     * @return
     */
    public static String queryContractHistoryByContractAddr(String contractAddr) {
        String basePath = String.format(QUERY_CONTRACT_HISTORY, contractAddr);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }

    /**
     * @param contractAddr
     * @return
     */
    public static String queryContractStateAllByContractAddr(String contractAddr) {
        String basePath = String.format(QUERY_CONTRACT_STATE_All, contractAddr);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }


    /**
     * @param contractAddr
     * @param query
     * @return
     */
    public static String queryContractStateSmart(String contractAddr, String query) throws UnsupportedEncodingException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] textByte = query.getBytes("UTF-8");
        String encodedText = encoder.encodeToString(textByte);
        String basePath = String.format(QUERY_CONTRACT_STATE_SMART, contractAddr, encodedText);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }


    /**
     * @param contractAddr
     * @param key
     * @return
     */
    public static String queryContractStateRaw(String contractAddr, String key){
        String basePath = String.format(QUERY_CONTRACT_STATE_RAW, contractAddr, key);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }
}
