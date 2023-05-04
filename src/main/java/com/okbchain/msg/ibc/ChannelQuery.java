package com.okbchain.msg.ibc;


import com.okbchain.env.EnvInstance;
import com.okbchain.msg.ibc.channel.*;
import com.okbchain.msg.common.Paging;
import com.okbchain.msg.common.Result;
import com.okbchain.msg.ibc.channel.ConnectionChannelsResponse;
import com.okbchain.utils.HttpUtils;
import com.okbchain.utils.Utils;
import lombok.extern.slf4j.Slf4j;

/***
 * @author lixuqing
 * @date 2022-05-09
 * @ChannelQuery
 */
@Slf4j
public class ChannelQuery {


    //query for all channels on a network by chain ID
    private static final String CHANNELS_BASEPATH="/ibc/core/channel/v1/channels";

    //Channel queries an IBC Channel.
    //%s=channel_id %s=port_id
    private static final String CHANNEL_BASEPATH="/ibc/core/channel/v1/channels/%s/ports/%s";

    //query for the packet commitment given a sequence and channel ID on a network by chain ID
    private static final String PACKET_COMMITMENTS_BASEPATH = "/ibc/core/channel/v1/channels/%s/ports/%s/packet_commitments";

    //query all channels associated with a given connection on a network by chain ID
    private final static String CONNECTION_CHANNELS_BASEPATH = "/ibc/core/channel/v1/connections/%s/channels";




    /**
     *Channel queries an IBC Channel.
     */
    public static ChannelResponse queryChannel(String channelId, String portId){
        String basePath = String.format(CHANNEL_BASEPATH, channelId,portId);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + basePath);

        if (res.isSuccess()) {
            ChannelResponse channelResponse = Utils.serializer.fromJson(res.getData(), ChannelResponse.class);
            return channelResponse;
        }
        return null;
    }

    /**
     * Channels queries all the IBC channels of a chain.
     */
    public static ChannelsResponse queryChannels(Paging paging){
        String queryParmas = "key=" + paging.getKey() + "&offset=" + paging.getOffset() + "&limit=" + paging.getLimit() + "&count_total=" + paging.isCountTotal();
        StringBuffer stringBuffer = new StringBuffer(CHANNELS_BASEPATH + "?");
        stringBuffer.append(queryParmas);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            ChannelsResponse channelsResponse = Utils.serializer.fromJson(res.getData(), ChannelsResponse.class);
            return channelsResponse;
        }
        return null;
    }


    /**
     queries all the channels associated with a connection end.
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

    /***
     *PacketCommitment queries a stored packet commitment hash.
     */
    public static PacketCommitmentResponse queryPacketCommitment(String channelId, String portId, String packetSequence) {
        String basePath = String.format(PACKET_COMMITMENTS_BASEPATH, channelId, portId);
        StringBuffer stringBuffer = new StringBuffer(basePath + "/");
        stringBuffer.append(packetSequence);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            PacketCommitmentResponse packetCommitmentResponse = Utils.serializer.fromJson(res.getData(), PacketCommitmentResponse.class);
            return packetCommitmentResponse;
        }
        return null;
    }
}
