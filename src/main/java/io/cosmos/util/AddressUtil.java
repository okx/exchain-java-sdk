package io.cosmos.util;

import com.google.common.primitives.Bytes;
import com.google.crypto.tink.subtle.Bech32;
import io.common.crypto.encode.ConvertBits;
import io.common.crypto.hash.Ripemd;
import io.common.exception.AddressFormatException;
import io.cosmos.common.Constants;
import org.apache.commons.codec.DecoderException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: coin-parent-sdk
 * @description: 地址相关的工具
 * @author: liqiang
 * @create: 2018-12-17 11:31
 **/
public class AddressUtil {

    public static String createNewAddressEd25519(String mainPrefix, byte[] publickKey) throws Exception {
        String addressResult = null;
        try {
            byte[] pubKeyHash = sha256Hash(publickKey, 0, publickKey.length);
            byte[] address = new byte[20];
            System.arraycopy(pubKeyHash, 0, address, 0, address.length);
            byte[] bytes = encode(0, address);
            addressResult = io.common.crypto.encode.Bech32.encode(mainPrefix, bytes);
            System.out.println(addressResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return addressResult;

    }

    public static String createNewAddressSecp256k1(String mainPrefix, byte[] publickKey) throws Exception {
        String addressResult = null;
        try {
            byte[] pubKeyHash = sha256Hash(publickKey, 0, publickKey.length);
            byte[] address = Ripemd.ripemd160(pubKeyHash);
            byte[] bytes = encode(0, address);
            addressResult = io.common.crypto.encode.Bech32.encode(mainPrefix, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return addressResult;

    }

    public static String createNewAddressSecp256k1Multi(byte[] publickKey) throws Exception {
        String addressResult = null;
        try {
            byte[] aminoWrap = EncodeUtils.aminoWrap(publickKey, Constants.COSMOS_PUBLIC_PREFIX.getBytes(), true);
            System.out.println(Hex.toHexString(aminoWrap));
            byte[] pubKeyHash = sha256Hash(aminoWrap, 0, aminoWrap.length);
            byte[] address = Ripemd.ripemd160(pubKeyHash);
            byte[] bytes = encode(0, address);
            addressResult = io.common.crypto.encode.Bech32.encode(Constants.COSMOS_MAIN_PREFIX, bytes);
            System.out.println(addressResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return addressResult;

    }

    public static byte[] getPubkeyValue(byte[] publickKey) throws Exception {
        try {
            byte[] pubKeyHash = sha256Hash(publickKey, 0, publickKey.length);
            byte[] value = Ripemd.ripemd160(pubKeyHash);
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] decodeAddress(String address){
        byte[] dec = Bech32.decode(address).getData();
        return ConvertBits.convertBits(dec, 0, dec.length, 5, 8, false);
    }

    private static byte[] sha256Hash(byte[] input, int offset, int length) throws NoSuchAlgorithmException {
        byte[] result = new byte[32];
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(input, offset, length);
        return digest.digest();
    }

    private static byte[] marshalAlgorithm() {
        byte[] algorithm = new byte[16];
        byte[] algorithmValue = "ed25519".getBytes();
        for (int i=0; i<algorithmValue.length; i++) {
            Arrays.fill(algorithm, i, i+1, algorithmValue[i]);
        }
        return algorithm;
    }

    private static int putUvarint(byte[] buf, long x) {
        int i;
        for(i = 0; x >= 128L; ++i) {
            buf[i] = (byte)((int)(x | 128L));
            x >>= 7;
        }

        buf[i] = (byte)((int)x);
        return i + 1;
    }

    private static byte[] encode(int witnessVersion, byte[] witnessProgram) throws AddressFormatException {
        byte[] convertedProgram = ConvertBits.convertBits(witnessProgram, 0, witnessProgram.length, 8, 5, true);
        return convertedProgram;
    }

//    public static void main(String[] args) {
//        try {
//            AddressUtil.createNewAddressEd25519(Hex.decode("73e7c47079fb857d524856ccb48ae2f769d79bfb08020a6b16c16250adf1595b"));
//            AddressUtil.createNewAddressSecp256k1(Hex.decode("02141202d82740af1b754b442f5fd9c4a862d49b5cafca5d04a9f06216ddf545bd"));
//            AddressUtil.createNewAddressSecp256k1Multi(Hex.decode("039ef93bad5b93f9dfd8bdd8149b5ecb9d571863a1aa165fa7b074a8d6697da5c8"));
//        } catch (Exception e) {
//
//        }
//    }
}
