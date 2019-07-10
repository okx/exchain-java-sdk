package com.okchain.client.impl;

import com.okchain.common.ConstantIF;
import com.okchain.common.jsonrpc.JSONRPCUtils;
import com.okchain.transaction.BuildTransaction;

import java.util.Map;
import java.util.TreeMap;

public class OKChainRPCClientImpl {
    private String backend;

    private static OKChainRPCClientImpl oKChainRPCClientImpl;

    private OKChainRPCClientImpl(String backend) {
        this.backend = backend;
    }

    public static OKChainRPCClientImpl getOKChainClient(String backend) throws NullPointerException {
        if (oKChainRPCClientImpl == null) {
            oKChainRPCClientImpl = new OKChainRPCClientImpl(backend);
        }
        return oKChainRPCClientImpl;
    }

    public String sendTransaction(byte[] data) {
        String method = "";

        switch (BuildTransaction.getMode()) {
            case ConstantIF.TX_SEND_MODE_BLOCK:
                method = ConstantIF.RPC_METHOD_TX_SEND_BLOCK;
                break;
            case ConstantIF.TX_SEND_MODE_SYNC:
                method = ConstantIF.RPC_METHOD_TX_SEND_SYNC;
                break;
            case ConstantIF.TX_SEND_MODE_ASYNC:
                method = ConstantIF.RPC_METHOD_TX_SEND_ASYNC;
                break;
            default:
                throw new NullPointerException("invalid TX_SEND_MODE");
        }
        Map<String, Object> mp = new TreeMap<>();
        mp.put("tx", data);
        String res = JSONRPCUtils.call(this.backend, method, mp);
        return res;
    }
}
