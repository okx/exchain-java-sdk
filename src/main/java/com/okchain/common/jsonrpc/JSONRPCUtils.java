package com.okchain.common.jsonrpc;

import com.alibaba.fastjson.JSON;
import com.okchain.common.HttpUtils;
import com.okchain.common.jsonrpc.types.RPCRequest;

import java.util.Map;

public class JSONRPCUtils {

    public static String call(String url, String method, Map<String, Object> params) {
        String data = getRpcSendData(method, params);
        return HttpUtils.httpPost(url, data);
    }

    public static String getRpcSendData(String method, Map<String, Object> params) {
        String id = "jsonrpc-client";
        String version = "2.0";
        RPCRequest rpcRequest = new RPCRequest(version, id, method, params);
        String data = JSON.toJSONString(rpcRequest);
        System.out.println(data);
        return data;
    }
}
