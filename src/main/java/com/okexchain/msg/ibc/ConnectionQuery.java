package com.okexchain.msg.ibc;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Paging;
import com.okexchain.msg.common.Result;
import com.okexchain.msg.ibc.connection.*;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;

/***
 * @author lixuqing
 * @date 2022-05-10
 * @ConnectionChannelsQuery
 */
public class ConnectionQuery {

    //%s=client_id
    private final static String CLIENT_CONNECTIONS_BASEPATH = "/ibc/core/connection/v1/client_connections/%s";

    private final static String CONNECTIONS_BASEPATH = "/ibc/core/connection/v1/connections";
    //%s=connection_id
    private final static String CONNECTION_BASEPATH = "/ibc/core/connection/v1/connections/%s";


    /**
     * Connection queries an IBC connection end.
     *
     * @return
     */
    public static ConnectionResponse queryConnection(String connectionId) {
        String basePath = String.format(CONNECTION_BASEPATH, connectionId);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + basePath);
        if (res.isSuccess()) {
            ConnectionResponse connectionResponse = Utils.serializer.fromJson(res.getData(), ConnectionResponse.class);
            return connectionResponse;
        }
        return null;
    }


    /**
     * Connections queries all the IBC connections of a chain.
     */
    public static ConnectionsResponse queryConnections(Paging paging) {
        String queryParmas = "key=" + paging.getKey() + "&offset=" + paging.getOffset() + "&limit=" + paging.getLimit() + "&count_total=" + paging.isCountTotal();
        StringBuffer stringBuffer = new StringBuffer(CONNECTIONS_BASEPATH + "?");
        stringBuffer.append(queryParmas);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            ConnectionsResponse connectionsResponse = Utils.serializer.fromJson(res.getData(), ConnectionsResponse.class);
            return connectionsResponse;
        }
        return null;
    }

    /**
     * ClientConnections queries the connection paths associated with a client state.
     *
     * @param clientId
     * @return
     */
    public static ClientConnectionsResponse queryClientConnections(String clientId) {
        String basePath = String.format(CLIENT_CONNECTIONS_BASEPATH, clientId);

        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + basePath);
        if (res.isSuccess()) {
            ClientConnectionsResponse clientConnectionsResponse = Utils.serializer.fromJson(res.getData(), ClientConnectionsResponse.class);
            return clientConnectionsResponse;
        }
        return null;
    }
}
