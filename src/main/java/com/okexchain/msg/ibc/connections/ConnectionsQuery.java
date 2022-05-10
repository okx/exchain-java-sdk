package com.okexchain.msg.ibc.connections;


import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.ibc.Height;
import com.okexchain.msg.ibc.Paging;
import com.okexchain.msg.ibc.Result;
import com.okexchain.msg.ibc.connections.pojo.*;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;

/***
 * @author lixuqing
 * @date 2022-05-10
 * @ConnectionChannelsQuery
 */
public class ConnectionsQuery {

    private final static String CONNECTION_CHANNELS_BASEPATH = "/ibc/core/channel/v1/connections/%s/channels";

    private final static String CONNECTIONS_BASEPATH = "/ibc/core/connection/v1/connections";

    private final static String CONNECTION_BASEPATH = "/ibc/core/connection/v1/connections/%s";

    private final static String CONNECTION_CLIENTSTATE_BASEPATH="/ibc/core/connection/v1/connections/%s/client_state";

    private final static String CONNECTION_CONSENSUSSTATE_BASEPATH="/ibc/core/connection/v1/connections/%s/consensus_state/revision/%s/height/%s";
    /**
     * @param connection
     * @param paging
     * @return
     * @ConnectionChannels queries all the channels associated with a connection end.
     */
    public static ConnectionChannelsResponse queryConnectionChannels(String connection, Paging paging) {
        String basePath = String.format(CONNECTION_CHANNELS_BASEPATH, connection);
        String queryParmas = "key=" + paging.getKey() + "&offset=" + paging.getOffset() + "&limit=" + paging.getLimit() + "&count_total=" + paging.isCountTotal();
        StringBuffer stringBuffer = new StringBuffer(basePath + "?");
        stringBuffer.append(queryParmas);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            ConnectionChannelsResponse connectionChannelsResponse = Utils.serializer.fromJson(res.getData(), ConnectionChannelsResponse.class);
            return connectionChannelsResponse;
        }
        return null;
    }

    /**
     * Connections queries all the IBC connections of a chain.
     *
     */
    public static ConnectionsResponse queryConnections() {
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + CONNECTIONS_BASEPATH);
        if (res.isSuccess()) {
            ConnectionsResponse connectionsResponse = Utils.serializer.fromJson(res.getData(), ConnectionsResponse.class);
            return connectionsResponse;
        }
        return null;
    }


    /**
     * Connection queries an IBC connection end.
     * @return
     */
    public static ConnectionResponse queryConnection(String connectionId){
        String basePath = String.format(CONNECTION_BASEPATH, connectionId);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + basePath);
        if (res.isSuccess()) {
            ConnectionResponse connectionResponse = Utils.serializer.fromJson(res.getData(), ConnectionResponse.class);
            return connectionResponse;
        }
        return null;
    }



    /**
     * ConnectionClientState queries the client state associated with the connection.
     * @return
     */
    public static ConnectionClientStateResponse queryClientState(String connectionId){
        String basePath = String.format(CONNECTION_CLIENTSTATE_BASEPATH, connectionId);
        System.out.println(EnvInstance.getEnv().GetRestServerUrl() + basePath);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + basePath);
        if (res.isSuccess()) {
            ConnectionClientStateResponse connectionClientStateResponse = Utils.serializer.fromJson(res.getData(), ConnectionClientStateResponse.class);
            return connectionClientStateResponse;
        }
        return null;
    }


    /**
     * ConnectionConsensusState queries the consensus state associated with the connection.
     * @return
     */
    public static ConnectionConsensusStateResponse queryConnectionConsensusState(String connectionId, Height height){
        String basePath = String.format(CONNECTION_CONSENSUSSTATE_BASEPATH, connectionId,height.getRevisionNumber(),height.getRevisionHeight());
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + basePath);
        System.out.println(res.getData());
        if (res.isSuccess()) {
            ConnectionConsensusStateResponse connectionConsensusStateResponse = Utils.serializer.fromJson(res.getData(), ConnectionConsensusStateResponse.class);
            return connectionConsensusStateResponse;
        }
        return null;
    }



    public static void main(String[] args) {
        EnvBase env = EnvInstance.getEnv();
        env.setRestServerUrl("https://api.cosmos.network");
       //ConnectionsQuery.queryClientState("connection-10");
        System.out.println(Utils.serializer.toJson(ConnectionsQuery.queryClientState("connection-10")));
    }
}
