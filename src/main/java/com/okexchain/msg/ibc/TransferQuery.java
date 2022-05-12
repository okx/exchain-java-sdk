package com.okexchain.msg.ibc;

import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Paging;
import com.okexchain.msg.ibc.transfer.DenomTraceResponse;
import com.okexchain.msg.ibc.transfer.DenomTracesResponse;
import com.okexchain.msg.ibc.transfer.ParamsResponse;
import com.okexchain.msg.common.Result;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;

public class TransferQuery {
    private static final String DENOM_TRACE_URL = "/ibc/apps/transfer/v1/denom_traces";
    private static final String DENOM_TRACES_URL = "/ibc/apps/transfer/v1/denom_traces";
    private static final String PARAMS_URL = "/ibc/apps/transfer/v1/params";

    public static void main(String[] args) {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("ibc-1");
//      env.setTxUrlPath("/cosmos/tx/v1beta1/txs");
        env.setRestServerUrl("http://127.0.0.1:10001");
        env.setRestPathPrefix("/exchain/v1");
        env.setTxUrlPath("/exchain/v1/txs");
        DenomTraceResponse response = new TransferQuery().queryDenomTrace("CD3872E1E59BAA23BDAB04A829035D4988D6397569EC77F1DC991E4520D4092B");
        System.out.println(response.getDenomTrace());
        Paging paging =new Paging();
        DenomTracesResponse denomTracesResponse = new TransferQuery().queryDenomTraces(paging);
        System.out.println(denomTracesResponse);

        ParamsResponse paramsResponse = new TransferQuery().queryParams();
        System.out.println(paramsResponse);
    }

    public DenomTraceResponse queryDenomTrace(String hash) {
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + DENOM_TRACE_URL + "/" + hash);
        if (res.isSuccess()) {
            DenomTraceResponse response = Utils.serializer.fromJson(res.getData(), DenomTraceResponse.class);
            return response;
        }
        return null;
    }

    public DenomTracesResponse queryDenomTraces(Paging paging) {
        String queryParmas = "key=" + paging.getKey() + "&offset=" + paging.getOffset() + "&limit=" + paging.getLimit() + "&count_total=" + paging.isCountTotal();
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + DENOM_TRACES_URL + "?" + queryParmas);
        if (res.isSuccess()) {
            DenomTracesResponse response = Utils.serializer.fromJson(res.getData(), DenomTracesResponse.class);
            return response;
        }
        return null;
    }

    public ParamsResponse queryParams() {
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + PARAMS_URL);
        if (res.isSuccess()) {
            ParamsResponse response = Utils.serializer.fromJson(res.getData(), ParamsResponse.class);
            return response;
        }
        return null;
    }
}