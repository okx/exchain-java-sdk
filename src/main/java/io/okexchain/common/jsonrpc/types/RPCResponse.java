package io.okexchain.common.jsonrpc.types;

import com.alibaba.fastjson.JSONObject;

public class RPCResponse {
    String jsonrpc;
    String id;
    JSONObject result;
    RPCError error;
}

