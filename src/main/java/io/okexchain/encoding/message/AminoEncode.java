package io.okexchain.encoding.message;

import io.okexchain.encoding.EncodeUtils;
import io.okexchain.proto.Transfer;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;

public class AminoEncode {
    public static byte[] encodeMsgSend(Transfer.MsgSend msgSendProto) throws IOException {
        return EncodeUtils.aminoWrap(msgSendProto.toByteArray(), MessageType.Send.getTypePrefixBytes(), false);
    }

    public static byte[] encodeMsgMultiSend(Transfer.MsgMultiSend msgMultiSendProto) throws IOException {
        return EncodeUtils.aminoWrap(msgMultiSendProto.toByteArray(), MessageType.MultiSend.getTypePrefixBytes(), false);
    }

    public static byte[] encodeMsgMultiNewOrder(Transfer.MsgMultiNewOrder msgMultiNewOrder) throws IOException {
        return EncodeUtils.aminoWrap(msgMultiNewOrder.toByteArray(), MessageType.MultiNewOrder.getTypePrefixBytes(), false);
    }

    public static byte[] encodeMsgMultiCancelOrder(Transfer.MsgMultiCancelOrder msgMultiCancelOrder) throws IOException {
        return EncodeUtils.aminoWrap(msgMultiCancelOrder.toByteArray(), MessageType.MultiCancelOrder.getTypePrefixBytes(), false);
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
