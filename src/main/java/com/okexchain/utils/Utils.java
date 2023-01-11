package com.okexchain.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.okexchain.utils.crypto.AddressConvertUtil;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.*;
import java.util.zip.GZIPOutputStream;

import static com.google.common.io.ByteStreams.toByteArray;

public class Utils {

    public static String rfc3339DateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public static int Precision = 18;

    public static final Gson serializer = new GsonBuilder().serializeNulls().setDateFormat(rfc3339DateFormat).disableHtmlEscaping().create();

    public static int writeVarint(long value, ByteArrayOutputStream stream) throws IOException {
        byte[] varint = new byte[8];
        int n = putUvarint(varint, value);
        stream.write(varint);
        return n;
    }

    public static int writeVarint64(long value, ByteArrayOutputStream stream) throws IOException {
        byte[] varint = new byte[8];
        int n = putUvarint(varint, value);
        byte[] varintTime = Arrays.copyOf(varint, n);
        stream.write(varintTime);
        return n;
    }

    public static int writeVarStr(byte[] buf, ByteArrayOutputStream stream) throws IOException {
        int n = writeVarint(buf.length, stream);
        stream.write(buf);
        return n + (buf.length);
    }

    public static int getLengthVarInt(long x) {
        byte[] varint = new byte[8];
        int n = putUvarint(varint, x);
        byte[] varintTime = Arrays.copyOf(varint, n);
        return varintTime.length;
    }

    public static int putUvarint(byte[] buf, long x) {
        int i = 0;
        while (x >= 0x80) {
            buf[i] = (byte) (x | 0x80);
            x >>= 7;
            i++;
        }
        buf[i] = (byte) x;
        return i + 1;
    }

    public static int writeVarBigInt(BigInteger value, ByteArrayOutputStream stream) throws IOException {
        byte[] bigInt = BigIntegerToBytes(value);
        int prefix = 0;
        boolean finded = false;
        for (int i = bigInt.length - 1; i >= 0; i--) {// get raw bits and seek to first zero byte
            if (bigInt[i] == 0x00) {
                prefix = i;
                finded = true;
                break;
            }
        }
        if (value.compareTo(new BigInteger("0")) > 0) {
            if (!finded) {
                prefix = bigInt.length - 1;
            }
        } else {
            throw new IOException("algorithm not support to marshal a negative bigint");
        }
        byte[] varint = new byte[8];
        int n = putUvarint(varint, prefix + 1); //write length prefix
        stream.write(varint);
        stream.write(bigInt);
        return n;
    }

    public static byte[] BigIntegerToBytes(BigInteger value) {
        if (value == null) {
            return null;
        } else {
            byte[] data = value.toByteArray();
            if (data.length != 1 && data[0] == 0) {
                byte[] tmp = new byte[data.length - 1];
                System.arraycopy(data, 1, tmp, 0, tmp.length);
                data = tmp;
            }

            return data;
        }
    }

    public static String NewDecString(String str) {
        if (str.length() == 0) {
            return "";
        }

        String[] strs = str.split("\\.");
        int lenDecs = 0;
        String combinedStr = strs[0];

        if (strs.length == 2) {
            lenDecs = strs[1].length();
            if (lenDecs == 0 || combinedStr.length() == 0) {
                return "";
            }
        } else if (strs.length > 2) {
            return "";
        } else {
            str += ".";
        }

        if (lenDecs > Precision) {
            return "";
        }

        if (lenDecs == Precision) {
            return str;
        }

        // add some extra zero's to correct to the Precision factor
        int zerosToAdd = Precision - lenDecs;
        String format = "%0" + String.valueOf(zerosToAdd) + "d";
        String zeros = String.format(format, 0);
        str += zeros;
        return str;
    }

    //json str sort by key
    public static JSONObject getSortJson(String jsonStr) {
        JSONObject json = JSONObject.parseObject(jsonStr);
        if (Objects.isNull(json)) {
            return new JSONObject();
        }
        Set<String> keySet = json.keySet();
        SortedMap<String, Object> map = new TreeMap<>();
        for (String key : keySet) {
            Object value = json.get(key);
            if (Objects.nonNull(value) && value instanceof JSONArray) {
                JSONArray array = json.getJSONArray(key);
                JSONArray jsonArray = new JSONArray(new LinkedList<>());
                for (int i = 0; i < array.size(); i++) {
                    JSONObject sortJson = getSortJson(String.valueOf(array.getJSONObject(i)));
                    jsonArray.add(sortJson);
                }
                map.put(key, jsonArray);
            } else if (Objects.nonNull(value) && value instanceof JSONObject) {
                JSONObject sortJson = getSortJson(String.valueOf(json.getJSONObject(key)));
                map.put(key, sortJson);
            } else {
                map.put(key, value);
            }
        }
        return new JSONObject(map);
    }


    //compress wasm bytes file
    public static byte[] compressBytes(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] byteArray = toByteArray(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(byteArray);
            in.close();
            gzip.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }


    //convert contract address
    public static String convertContractAddress(String contractAddress) throws Exception {
        String headStr = contractAddress.substring(0, 2);
        if (headStr.equals("ex")) {
            return AddressConvertUtil.convertFromBech32ToHex(contractAddress);
        } else if (headStr.equals("0x")) {
            return contractAddress;
        } else {
            throw new Exception("address error:" + contractAddress);
        }
    }


    // if address is Hex address then convert From Hex To ExBech32
    public static String convertHexAddrToExBech32(String contractAddress) throws Exception {
        String headStr = contractAddress.substring(0, 2);
        if (headStr.equals("0x")) {
            return AddressConvertUtil.convertFromHexToExBech32(contractAddress);
        } else if (headStr.equals("ex")) {
            return contractAddress;
        } else {
            throw new Exception("address error:" + contractAddress);
        }
    }

    public static boolean isValidHexAddress(String hexAddress) {
        boolean result = false;

        if (hexAddress == null || "".equals(hexAddress)) {
            return result;
        }

        if (!hexAddress.startsWith("0x")) {
            return result;
        }

        String hex = hexAddress.substring(2);
        if (hex.length() != 40) {
            return result;
        }

        try {
            Hex.decode(hex);
            result = true;
        } catch (Exception e) {

        }
        return result;
    }
}
