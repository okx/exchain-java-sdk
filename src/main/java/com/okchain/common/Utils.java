package com.okchain.common;

import org.bouncycastle.crypto.digests.Blake2bDigest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class Utils {

    public final static String rfc3339DateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    //public final static Gson serializer = new GsonBuilder().setDateFormat(rfc3339DateFormat).disableHtmlEscaping().create();

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
        for (int i=bigInt.length-1; i>=0; i--) {// get raw bits and seek to first zero byte
            if (bigInt[i] == 0x00) {
                prefix = i;
                finded = true;
                break;
            }
        }
        if (value.compareTo(new BigInteger("0")) > 0) {
            if (!finded) {
                prefix = bigInt.length-1;
            }
        } else {
            throw new IOException("algorithm not support to marshal a negative bigint");
        }
        byte[] varint = new byte[8];
        int n = putUvarint(varint, prefix+1); //write length prefix
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

    public static byte[] pruneIntermediateScalar(byte[] f) {
        f[0] &= 248;
        f[31] &= 63; // clear top 3 bits
        f[31] |= 64; // set second highest bit
        return f;
    }

    public static byte[] newHashBlake2B() {
        byte[] result = new byte[32];
        byte[] key = new byte[0];
        Blake2bDigest blake2bDigest = new Blake2bDigest(256);
        blake2bDigest.update(key, 0, 0);
        blake2bDigest.doFinal(result,0);
        return result;
    }

    public static byte[] hashBlake2B(byte[] input1, int offset1, int length1) {
        byte[] result = new byte[32];
        Blake2bDigest blake2bDigest = new Blake2bDigest(null, result.length, null,null);
        blake2bDigest.update(input1,offset1, length1);
        blake2bDigest.doFinal(result,0);
        return result;
    }

    public static byte[] hashBlake2BTwice(byte[] input1, int offset1, int length1,
                                    byte[] input2, int offset2, int length2) {
        byte[] result = new byte[32];
        Blake2bDigest blake2bDigest = new Blake2bDigest(null, result.length, null,null);
        blake2bDigest.update(input1,offset1, length1);
        blake2bDigest.update(input2,offset2, length2);
        blake2bDigest.doFinal(result,0);
        return result;
    }

    public static byte[] hashBlake2BThird(byte[] input1, int offset1, int length1,
                                    byte[] input2, int offset2, int length2,
                                    byte[] input3, int offset3, int length3) {
        byte[] result = new byte[32];
        Blake2bDigest blake2bDigest = new Blake2bDigest(null, result.length, null,null);
        blake2bDigest.update(input1,offset1, length1);
        blake2bDigest.update(input2,offset2, length2);
        blake2bDigest.update(input3,offset3, length3);
        blake2bDigest.doFinal(result,0);
        return result;
    }

    public static byte[] marshalAlgorithm() {
        byte[] algorithm = new byte[16];
        byte[] algorithmValue = "ed25519".getBytes();
        for (int i=0; i<algorithmValue.length; i++) {
            Arrays.fill(algorithm, i, i+1, algorithmValue[i]);
        }
        return algorithm;
    }

    public static byte[] marshalInt(long x) {
        byte[] varInt = new byte[8];
        putUvarint(varInt, x);
        return hashBlake2B(varInt, 0 , varInt.length);
    }

}
