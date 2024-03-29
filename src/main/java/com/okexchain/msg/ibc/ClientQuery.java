package com.okexchain.msg.ibc;

import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.ibc.client.ClientStateResponse;
import com.okexchain.msg.ibc.client.ClientStatesResponse;
import com.okexchain.msg.common.Paging;
import com.okexchain.msg.common.Result;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;

public class ClientQuery {


    //%s=client_id
    private static final String CLIENTSTATE_BASEPATH="/ibc/core/client/v1/client_states/%s";

    private static final String CLIENTSTATES_BASEPATH="/ibc/core/client/v1/client_states";


    /**
     * ClientState queries an IBC light client.
     */
    public static ClientStateResponse queryClientState(String clientId){
        String basePath = String.format(CLIENTSTATE_BASEPATH, clientId);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + basePath);
        if (res.isSuccess()) {
            ClientStateResponse clientStateResponse = Utils.serializer.fromJson(res.getData(), ClientStateResponse.class);
            return clientStateResponse;
        }
        return null;
    }


    /**
     * ClientStates queries all the IBC light clients of a chain.
     */
    public static ClientStatesResponse queryClientStates(Paging paging){
        String queryParmas = "key=" + paging.getKey() + "&offset=" + paging.getOffset() + "&limit=" + paging.getLimit() + "&count_total=" + paging.isCountTotal();
        StringBuffer stringBuffer = new StringBuffer(CLIENTSTATES_BASEPATH + "?");
        stringBuffer.append(queryParmas);
        System.out.println(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            ClientStatesResponse clientStatesResponse = Utils.serializer.fromJson(res.getData(), ClientStatesResponse.class);
            return clientStatesResponse;
        }
        return null;
    }
}
