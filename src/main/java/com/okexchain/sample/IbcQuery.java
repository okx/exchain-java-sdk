package com.okexchain.sample;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Paging;
import com.okexchain.msg.ibc.ChannelQuery;
import com.okexchain.msg.ibc.ConnectionQuery;
import com.okexchain.msg.ibc.TxQuery;
import com.okexchain.msg.ibc.channel.ChannelResponse;
import com.okexchain.msg.ibc.channel.ChannelsResponse;
import com.okexchain.msg.ibc.channel.ConnectionChannelsResponse;
import com.okexchain.msg.ibc.channel.PacketCommitmentResponse;
import com.okexchain.msg.ibc.connection.ConnectionsResponse;
import com.okexchain.msg.ibc.tx.TxEvent;
import com.okexchain.utils.Utils;

public class IbcQuery {

    public static void main(String []args){
        EnvBase env = EnvInstance.getEnv();
        env.setRestServerUrl("http://127.0.0.1:36659");
        env.setRestPathPrefix("/exchain/v1");

        //IbcQuery.queryTxs();
        IbcQuery.queryChannel();
        //IbcQuery.queryConnetion();
    }

    public static void queryTxs(){
        JSONObject obj= TxQuery.queryTx("9C13FDCF38318CAC04321E45B917D3466A42C5A78F30FEEA76B0D68996A37B69");
        System.out.println(obj);
        TxEvent txEvent=new TxEvent("send","ex1jvq7qm8dc47ckdjgs0aeku6w5xdfzswhzjup5m","1","10","1","1000000");
        System.out.println(TxQuery.queryTxs(txEvent));
    }

    public static void queryChannel(){

        ChannelResponse channelResponse=ChannelQuery.queryChannel("channel-0","transfer");
        System.out.println(Utils.serializer.toJson(channelResponse));

        ChannelsResponse channelsResponse=ChannelQuery.queryChannels(new Paging());
        System.out.println(Utils.serializer.toJson(channelsResponse));

        ConnectionChannelsResponse connectionChannelsResponse=ChannelQuery.queryConnectionChannels("connection-0",new Paging());
        System.out.println(Utils.serializer.toJson(connectionChannelsResponse));

        PacketCommitmentResponse packetCommitmentResponse=ChannelQuery.queryPacketCommitment("channel-0","transfer","0");
        System.out.println(Utils.serializer.toJson(packetCommitmentResponse));
    }

    public static void queryConnetion(){
        ConnectionsResponse connectionsResponse=ConnectionQuery.queryConnections(new Paging());
    }
}
