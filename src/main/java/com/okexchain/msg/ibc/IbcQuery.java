package com.okexchain.msg.ibc;

import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;

public class IbcQuery {
    public static final String DENOM_TRACE_URL = "/ibc/apps/transfer/v1/denom_traces";
    public static final String DENOM_TRACES_URL = "/ibc/apps/transfer/v1/denom_traces";

    public static void main(String[] args) {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("ibc-1");
//        env.setTxUrlPath("/cosmos/tx/v1beta1/txs");
        env.setRestServerUrl("http://127.0.0.1:10001");
        env.setRestPathPrefix("/exchain/v1");
        env.setTxUrlPath("/exchain/v1/txs");
        DenomTraceResponse response = new IbcQuery().queryDenomTrace("CD3872E1E59BAA23BDAB04A829035D4988D6397569EC77F1DC991E4520D4092B");
        System.out.println(response.getDenomTrace());

        DenomTracesResponse denomTracesResponse = new IbcQuery().queryDenomTraces(null,0,0,true);
        System.out.println(denomTracesResponse);
    }

    public DenomTraceResponse queryDenomTrace(String hash) {

        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + DENOM_TRACE_URL + "/" + hash);

        if (res.isSuccess()) {
            DenomTraceResponse response = Utils.serializer.fromJson(res.getData(), DenomTraceResponse.class);
            return response;
        }

        return null;
    }

    public DenomTracesResponse queryDenomTraces(String nextKey, int offset, int limit, boolean countTotal) {
        String queryParmas = "key=" + nextKey + "&offset="+offset + "&limit=" + limit + "&count_total";

        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + DENOM_TRACES_URL + "?" + queryParmas);
        if (res.isSuccess()) {
            DenomTracesResponse response = Utils.serializer.fromJson(res.getData(), DenomTracesResponse.class);
            return response;
        }

        return null;
    }


}
