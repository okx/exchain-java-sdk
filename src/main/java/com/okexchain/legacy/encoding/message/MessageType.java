package com.okexchain.legacy.encoding.message;

import com.okexchain.legacy.encoding.EncodeUtils;

public enum MessageType {
    BaseType(null),
    Send("5605A9BA"),
    MultiSend("D6B821E4"),
    MultiNewOrder("66A24314"),
    MultiCancelOrder("5B043F67"),
    PubKey("EB5AE987"),
    StdTx("282816A9");

    private byte[] typePrefixBytes;

    MessageType(String typePrefix) {
        if (typePrefix == null) {
            this.typePrefixBytes = new byte[0];
        } else
            this.typePrefixBytes = EncodeUtils.hexStringToByteArray(typePrefix);
    }

    public byte[] getTypePrefixBytes() {
        return typePrefixBytes;
    }

}
