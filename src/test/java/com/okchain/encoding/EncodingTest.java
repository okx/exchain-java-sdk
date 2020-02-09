package com.okchain.encoding;

import com.okchain.encoding.message.MessageType;
import com.okchain.proto.Transfer;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.IOException;

public class EncodingTest {
    @Test
    public void testAminoBaseType() throws IOException {
        Transfer.BaseType btProto = Transfer.BaseType.newBuilder()
                .setI(500000000)
                .setS("okchain")
                .addSs("btc").addSs("okb")
                .addStus(Transfer.Stu.newBuilder().setId(500000000).build())
                .addStus(Transfer.Stu.newBuilder().setId(500000000).build())
                .build();
        System.out.println(btProto);
        byte[] rawBytesProtoEncoded = EncodeUtils.aminoWrap(btProto.toByteArray(), MessageType.BaseType.getTypePrefixBytes(), true);
        System.out.println(Hex.toHexString(rawBytesProtoEncoded));
    }

    @Test
    public void testBigInt() throws IOException {
        Transfer.Stu stuProto = Transfer.Stu.newBuilder().setId(500000000).build();
        byte[] rawBytesProtoEncoded = EncodeUtils.aminoWrap(stuProto.toByteArray(), MessageType.BaseType.getTypePrefixBytes(), true);
        System.out.println(Hex.toHexString(rawBytesProtoEncoded));
        String str = EncodeUtils.stringTo8("5.00112212121212");
        System.out.println(str);
    }
}
