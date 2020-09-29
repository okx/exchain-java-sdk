package io.okexchain.common.jsonrpc;

import com.alibaba.fastjson.JSON;
import io.okexchain.common.HttpUtils;
import io.okexchain.common.jsonrpc.types.RPCRequest;
import io.okexchain.exception.OKEXChainException;

import java.util.Map;

public class JSONRPCUtils {

    public static String call(String url, String method, Map<String, Object> params) {
        String data = getRpcSendData(method, params);
        String res = HttpUtils.httpPost(url, data);
        try{
            JSON.parseObject(res);
        }catch (Exception e){
            throw new OKEXChainException(res);
        }
        return res;
    }

    public static String getRpcSendData(String method, Map<String, Object> params) {
        String id = "jsonrpc-client";
        String version = "2.0";
        RPCRequest rpcRequest = new RPCRequest(version, id, method, params);
        String data = JSON.toJSONString(rpcRequest);
        return data;
    }
}
