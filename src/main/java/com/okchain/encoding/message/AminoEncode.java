package com.okchain.encoding.message;

import com.okchain.encoding.EncodeUtils;
import com.okchain.proto.Transfer;
import com.okchain.types.Pubkey;
import com.okchain.types.Signature;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.List;

// write by michael.w 20190710
public class AminoEncode {
    public static byte[] encodeMsgNewOrder(Transfer.MsgNewOrder msgNewOrderProto) throws IOException {
        return EncodeUtils.aminoWrap(msgNewOrderProto.toByteArray(), MessageType.NewOrder.getTypePrefixBytes(), false);
    }

    public static byte[] encodeMsgCancelOrder(Transfer.MsgCancelOrder msgCancelOrderProto) throws IOException {
        return EncodeUtils.aminoWrap(msgCancelOrderProto.toByteArray(), MessageType.CancelOrder.getTypePrefixBytes(), false);
    }

    public static byte[] encodeMsgSend(Transfer.MsgSend msgSendProto) throws IOException {
        return EncodeUtils.aminoWrap(msgSendProto.toByteArray(), MessageType.Send.getTypePrefixBytes(), false);
    }
    public static byte[] encodeMsgMultiSend(Transfer.MsgMultiSend msgMultiSendProto) throws IOException{
        return EncodeUtils.aminoWrap(msgMultiSendProto.toByteArray(),MessageType.MultiSend.getTypePrefixBytes(),false);
    }
    public static byte[] encodePubkey(String pubkey) {
        byte[] pubKey = Hex.decode(pubkey);
        byte[] pubKeyPrefix = MessageType.PubKey.getTypePrefixBytes();
        byte[] pubKeyAminoEncoded = new byte[pubKey.length + pubKeyPrefix.length + 1];
        // 从pubKeyPrefix的索引0开始向pubKeyAminoEncoded的索引0位置开始复制长度为pubKeyPrefix.length个字节
        System.arraycopy(pubKeyPrefix, 0, pubKeyAminoEncoded, 0, pubKeyPrefix.length);
        pubKeyAminoEncoded[pubKeyPrefix.length] = (byte) 33;
        System.arraycopy(pubKey, 0, pubKeyAminoEncoded, pubKeyPrefix.length + 1, pubKey.length);
        return pubKeyAminoEncoded;
    }

    public static byte[] encodeStdTransaction(Transfer.StdTransaction stdTransactionProto) throws IOException {
        return EncodeUtils.aminoWrap(stdTransactionProto.toByteArray(), MessageType.StdTx.getTypePrefixBytes(), false);
    }
}
