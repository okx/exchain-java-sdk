package com.okchain.encoding.message;

import com.okchain.encoding.EncodeUtils;
import com.okchain.proto.Transfer;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

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

    public static byte[] encodeMsgMultiSend(Transfer.MsgMultiSend msgMultiSendProto) throws IOException {
        return EncodeUtils.aminoWrap(msgMultiSendProto.toByteArray(), MessageType.MultiSend.getTypePrefixBytes(), false);
    }

    public static byte[] encodePubkey(String pubkey) {
        byte[] pubKey = Hex.decode(pubkey);
        byte[] pubKeyPrefix = MessageType.PubKey.getTypePrefixBytes();
        byte[] pubKeyAminoEncoded = new byte[pubKey.length + pubKeyPrefix.length + 1];
        // copy 'pubKeyPrefix' into 'pubKeyAminoEncoded' starting from index 0 of 'pubKeyAminoEncoded' and go through 'pubKeyPrefix.length' bytes
        System.arraycopy(pubKeyPrefix, 0, pubKeyAminoEncoded, 0, pubKeyPrefix.length);
        pubKeyAminoEncoded[pubKeyPrefix.length] = (byte) 33;
        System.arraycopy(pubKey, 0, pubKeyAminoEncoded, pubKeyPrefix.length + 1, pubKey.length);
        return pubKeyAminoEncoded;
    }

    public static byte[] encodeStdTransaction(Transfer.StdTransaction stdTransactionProto) throws IOException {
        return EncodeUtils.aminoWrap(stdTransactionProto.toByteArray(), MessageType.StdTx.getTypePrefixBytes(), true);
    }
}
