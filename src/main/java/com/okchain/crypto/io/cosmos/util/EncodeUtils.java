package com.okchain.crypto.io.cosmos.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.protobuf.CodedOutputStream;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-20 11:55
 **/
public class EncodeUtils {

    private static final ObjectWriter OBJECT_WRITER;

    static {
        ObjectMapper mapper = new ObjectMapper();
        OBJECT_WRITER = mapper.writer();
    }

    public static byte[] hexStringToByteArray(String s) {
        return Hex.decode(s);
    }

    public static String bytesToHex(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

    public static String toJsonStringSortKeys(Object object) throws JsonProcessingException {
        return OBJECT_WRITER.writeValueAsString(object);
    }

    public static byte[] toJsonEncodeBytes(Object object) throws JsonProcessingException {
        return toJsonStringSortKeys(object).getBytes(Charset.forName("UTF-8"));
    }
}
