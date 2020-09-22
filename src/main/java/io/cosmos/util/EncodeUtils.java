package io.cosmos.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.spongycastle.util.encoders.Hex;

import java.nio.charset.Charset;

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
