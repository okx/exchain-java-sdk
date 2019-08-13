package com.okchain.encoding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.CodedOutputStream;
import org.spongycastle.util.encoders.Hex;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

public class EncodeUtils {
    private static final ObjectMapper OBJECT_MAPPER;
    private static final BigDecimal MULTIPLY_FACTOR = BigDecimal.valueOf(1e8);
    private static final BigDecimal MAX_NUMBER = new BigDecimal(Long.MAX_VALUE);


    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    public static byte[] hexStringToByteArray(String s) {
        return Hex.decode(s);
    }

    public static String bytesToHex(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

    public static String toJsonStringSortKeys(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T toObjectFromJsonString(String jsonString, Class<T> tClass) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString, tClass);
    }

    public static byte[] toJsonEncodeBytes(Object object) throws JsonProcessingException {
        return toJsonStringSortKeys(object).getBytes(Charset.forName("UTF-8"));
    }

    public static byte[] aminoWrap(byte[] raw, byte[] typePrefix, boolean isPrefixLength) throws IOException {
        int totalLen = raw.length + typePrefix.length;
        if (isPrefixLength)
            totalLen += CodedOutputStream.computeUInt64SizeNoTag(totalLen);

        byte[] msg = new byte[totalLen];
        CodedOutputStream cos = CodedOutputStream.newInstance(msg);
        if (isPrefixLength)
            cos.writeUInt64NoTag(raw.length + typePrefix.length);
        cos.write(typePrefix, 0, typePrefix.length);
        cos.write(raw, 0, raw.length);
        cos.flush();
        return msg;
    }

    public static String stringTo8(String d) {
        BigDecimal encodeValue = new BigDecimal(d).multiply(MULTIPLY_FACTOR);
        if (encodeValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(d + " is less or equal to zero.");
        }
        if (encodeValue.compareTo(MAX_NUMBER) > 0) {
            throw new IllegalArgumentException(d + " is too large.");
        }
        return String.valueOf(encodeValue.longValue());

    }

}
