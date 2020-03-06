package com.okchain.encoding;

import com.okchain.encoding.message.MessageType;
import com.okchain.proto.Transfer;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class EncodingTest {
    @Test
    public void aminoBaseTypeTest() throws IOException {
        Transfer.BaseType btProto = Transfer.BaseType.newBuilder()
                .setI(500000000)
                .setS("okchain")
                .addSs("btc").addSs("okb")
                .addStus(Transfer.Stu.newBuilder().setId(500000000).build())
                .addStus(Transfer.Stu.newBuilder().setId(500000000).build())
                .build();
        byte[] rawBytesProtoEncoded = EncodeUtils.aminoWrap(btProto.toByteArray(), MessageType.BaseType.getTypePrefixBytes(), true);
        Assert.assertEquals("290880cab5ee0112076f6b636861696e1a036274631a036f6b6222060880cab5ee0122060880cab5ee01",Hex.toHexString(rawBytesProtoEncoded));

    }

    @Test
    public void bigIntTest() throws IOException {
        Transfer.Stu stuProto = Transfer.Stu.newBuilder().setId(500000000).build();
        byte[] rawBytesProtoEncoded = EncodeUtils.aminoWrap(stuProto.toByteArray(), MessageType.BaseType.getTypePrefixBytes(), true);
        Assert.assertEquals("060880cab5ee01",Hex.toHexString(rawBytesProtoEncoded));
        String str = EncodeUtils.stringTo8("5.00112212121212");
        Assert.assertEquals("500112212",str);
    }
}
