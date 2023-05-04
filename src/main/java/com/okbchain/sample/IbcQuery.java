package com.okbchain.sample;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvBase;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Paging;
import com.okbchain.msg.ibc.*;
import com.okbchain.msg.ibc.channel.ChannelResponse;
import com.okbchain.msg.ibc.channel.ChannelsResponse;
import com.okbchain.msg.ibc.channel.ConnectionChannelsResponse;
import com.okbchain.msg.ibc.channel.PacketCommitmentResponse;
import com.okbchain.msg.ibc.client.ClientStateResponse;
import com.okbchain.msg.ibc.client.ClientStatesResponse;
import com.okbchain.msg.ibc.connection.ClientConnectionsResponse;
import com.okbchain.msg.ibc.connection.ConnectionResponse;
import com.okbchain.msg.ibc.connection.ConnectionsResponse;
import com.okbchain.msg.ibc.transfer.DenomTraceResponse;
import com.okbchain.msg.ibc.transfer.DenomTracesResponse;
import com.okbchain.msg.ibc.transfer.ParamsResponse;
import com.okbchain.msg.ibc.tx.TxEvent;
import com.okbchain.utils.Utils;

public class IbcQuery {

    public static void main(String[] args) {
        EnvBase env = EnvInstance.getEnv();
        env.setRestServerUrl("http://127.0.0.1:36659");
        env.setRestPathPrefix("/exchain/v1");

        IbcQuery.queryTxs();
        IbcQuery.queryChannel();
        IbcQuery.queryConnetion();
        IbcQuery.queryClient();
        IbcQuery.queryHeader();
        IbcQuery.queryTransfer();
    }

    public static void queryTxs() {
        JSONObject obj = TxQuery.queryTx("9C13FDCF38318CAC04321E45B917D3466A42C5A78F30FEEA76B0D68996A37B69");
        System.out.println(obj);
        TxEvent txEvent = new TxEvent("send", "ex1jvq7qm8dc47ckdjgs0aeku6w5xdfzswhzjup5m", "1", "10", "1", "1000000");
        System.out.println(TxQuery.queryTxs(txEvent));
    }

    public static void queryChannel() {

        ChannelResponse channelResponse = ChannelQuery.queryChannel("channel-0", "transfer");
        System.out.println(Utils.serializer.toJson(channelResponse));

        ChannelsResponse channelsResponse = ChannelQuery.queryChannels(new Paging());
        System.out.println(Utils.serializer.toJson(channelsResponse));

        ConnectionChannelsResponse connectionChannelsResponse = ChannelQuery.queryConnectionChannels("connection-0", new Paging());
        System.out.println(Utils.serializer.toJson(connectionChannelsResponse));

        PacketCommitmentResponse packetCommitmentResponse = ChannelQuery.queryPacketCommitment("channel-0", "transfer", "0");
        System.out.println(Utils.serializer.toJson(packetCommitmentResponse));
    }

    public static void queryConnetion() {
        ConnectionsResponse connectionsResponse = ConnectionQuery.queryConnections(new Paging());
        System.out.println(Utils.serializer.toJson(connectionsResponse));

        ConnectionResponse connectionResponse = ConnectionQuery.queryConnection("connection-0");
        System.out.println(Utils.serializer.toJson(connectionResponse));

        ClientConnectionsResponse clientConnectionsResponse = ConnectionQuery.queryClientConnections("07-tendermint-0");
        System.out.println(Utils.serializer.toJson(clientConnectionsResponse));
    }

    public static void queryClient() {

        ClientStatesResponse clientStatesResponse = ClientQuery.queryClientStates(new Paging());
        System.out.println(Utils.serializer.toJson(clientStatesResponse));

        ClientStateResponse clientStateResponse = ClientQuery.queryClientState("07-tendermint-0");
        System.out.println(Utils.serializer.toJson(clientStateResponse));

    }

    public static void queryHeader() {
        EnvBase env = EnvInstance.getEnv();
        env.setRestServerUrl("http://127.0.0.1:36657");
        JSONObject json = HeaderQuery.queryHeader("100");
        System.out.println(json);
    }

    public static void queryTransfer() {
        EnvBase env = EnvInstance.getEnv();
        env.setRestServerUrl("http://127.0.0.1:10001");
        DenomTraceResponse response = TransferQuery.queryDenomTrace("CD3872E1E59BAA23BDAB04A829035D4988D6397569EC77F1DC991E4520D4092B");
        System.out.println(response.getDenomTrace());
        Paging paging = new Paging();
        DenomTracesResponse denomTracesResponse = TransferQuery.queryDenomTraces(paging);
        System.out.println(denomTracesResponse);
        ParamsResponse paramsResponse = TransferQuery.queryParams();
        System.out.println(paramsResponse);
    }
}
