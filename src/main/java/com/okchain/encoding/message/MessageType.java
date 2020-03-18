package com.okchain.encoding.message;

import com.okchain.encoding.EncodeUtils;

public enum MessageType {
    BaseType(null),
    Send("F738C3C9"),
    MultiSend("0E7BC372"),
    MultiNewOrder("DC9650E5"),
    MultiCancelOrder("4867EBA0"),
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
