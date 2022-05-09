package com.okexchain.msg.ibc;

import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

/***
 * @author lixuqing
 * @date 2022-05-09
 * @packet-commit
 * @query for the packet commitment given a sequence and channel ID on a network by chain ID
 */
@Slf4j
public class PacketCommit {
    //PACKET_COMMIT API BASE URL
    private static String packetCommitmentsBasePath = "/ibc/core/channel/v1/channels/%s/ports/%s/packet_commitments";

    private static String packetReceiptBasePath="/ibc/core/channel/v1/channels/%s/ports/%s/packet_receipts";


    /***
     * PacketCommitments returns all the packet commitments hashes associated with a channel
     * @return
     */
    public static PacketCommitmentsResponse queryPacketCommitments(String channelId, String portId, Paging paging) {
        String basePath = String.format(packetCommitmentsBasePath, channelId, portId);
        String queryParmas = "key=" + paging.getKey() + "&offset=" + paging.getOffset() + "&limit=" + paging.getLimit() + "&count_total=" + paging.isCountTotal();
        StringBuffer stringBuffer = new StringBuffer(basePath + "?");
        stringBuffer.append(queryParmas);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            PacketCommitmentsResponse commitmentsResponse = Utils.serializer.fromJson(res.getData(), PacketCommitmentsResponse.class);
            return commitmentsResponse;
        }
        return null;
    }

    /***
     * UnreceivedAcks returns all the unreceived IBC acknowledgements associated with a channel and sequences.
     * @return
     */
    public static UnreceivedAcksResponse queryUnreceivedAcks(String channelId, String portId, List<String> acknowledgementSequences) {
        String basePath = String.format(packetCommitmentsBasePath, channelId, portId);
        String strTmp = "";
        for (int i = 0; i < acknowledgementSequences.size(); i++) {
            strTmp += acknowledgementSequences.get(i) + ",";
        }
        String sequences = strTmp.substring(0, strTmp.length() - 1);
        StringBuffer stringBuffer = new StringBuffer(basePath + "/");
        stringBuffer.append(sequences).append("/unreceived_acks");
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            UnreceivedAcksResponse unreceivedAcksResponse = Utils.serializer.fromJson(res.getData(), UnreceivedAcksResponse.class);
            return unreceivedAcksResponse;
        }
        return null;
    }

    /***
     * UnreceivedPackets returns all the unreceived IBC packets associated with a channel and sequences.
     * @return
     */
    public static UnreceivedPacketsResponse queryUnreceivedPackets(String channelId, String portId, List<String> packetSequences) {
        String basePath = String.format(packetCommitmentsBasePath, channelId, portId);
        String strTmp = "";
        for (int i = 0; i < packetSequences.size(); i++) {
            strTmp += packetSequences.get(i) + ",";
        }
        String sequences = strTmp.substring(0, strTmp.length() - 1);
        StringBuffer stringBuffer = new StringBuffer(basePath + "/");
        stringBuffer.append(sequences).append("/unreceived_packets");
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            UnreceivedPacketsResponse unreceivedPacketsResponse = Utils.serializer.fromJson(res.getData(), UnreceivedPacketsResponse.class);
            return unreceivedPacketsResponse;
        }
        return null;
    }

    /***
     *PacketCommitment queries a stored packet commitment hash
     */
    public static PacketCommitmentResponse queryPacketCommitment(String channelId, String portId, String packetSequence) {
        String basePath = String.format(packetCommitmentsBasePath, channelId, portId);
        StringBuffer stringBuffer = new StringBuffer(basePath + "/");
        stringBuffer.append(packetSequence);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            PacketCommitmentResponse packetCommitmentResponse = Utils.serializer.fromJson(res.getData(), PacketCommitmentResponse.class);
            return packetCommitmentResponse;
        }
        return null;
    }


    /**
     * PacketReceipt queries if a given packet sequence has been received on the queried chain
     * @return
     */
    public static PacketReceiptResponse queryPacketReceipt(String channelId, String portId, String packetSequence){
        String basePath = String.format(packetReceiptBasePath, channelId, portId);
        StringBuffer stringBuffer = new StringBuffer(basePath + "/");
        stringBuffer.append(packetSequence);
        System.out.println(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + stringBuffer);
        if (res.isSuccess()) {
            PacketReceiptResponse packetReceiptResponse = Utils.serializer.fromJson(res.getData(), PacketReceiptResponse.class);
            return packetReceiptResponse;
        }
        return null;
    }


    public static void main(String[] args) {
        EnvBase env = EnvInstance.getEnv();
        env.setRestServerUrl("https://api.cosmos.network");
        PacketReceiptResponse packetReceiptResponse = PacketCommit.queryPacketReceipt("channel-0", "transfer", "1");
        System.out.println(Utils.serializer.toJson(packetReceiptResponse));
    }
}
