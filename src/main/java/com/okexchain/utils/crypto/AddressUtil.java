package com.okexchain.utils.crypto;

import com.okexchain.utils.crypto.encode.Bech32;
import com.okexchain.utils.crypto.encode.ConvertBits;
import com.okexchain.utils.crypto.hash.Ripemd;
import com.okexchain.utils.exception.AddressFormatException;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AddressUtil {

    public static String createNewAddressSecp256k1(String mainPrefix, byte[] publickKey) throws Exception {
        //get address
        String address = Keys.getAddress(new BigInteger(publickKey));

        //get okexchain
        String addressResult = null;
        try {
            byte[] bytes = encode(0, Hex.decode(address));
            addressResult = com.okexchain.utils.crypto.encode.Bech32.encode(mainPrefix, bytes);
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

    public static String getPubkeyBech32FromValue(String mainPrefix, byte[] publickKeyValue) throws Exception {
        try {
            byte[] bytes = encode(0, publickKeyValue);
            String pubBech32 = com.okexchain.utils.crypto.encode.Bech32.encode(mainPrefix, bytes);
            return pubBech32;
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

    private static byte[] encode(int witnessVersion, byte[] witnessProgram) throws AddressFormatException {
        byte[] convertedProgram = ConvertBits.convertBits(witnessProgram, 0, witnessProgram.length, 8, 5, true);
        return convertedProgram;
    }


    public static String createNewAddressEthSecp256k1(String mainPrefix, byte[] address) throws Exception {
        String addressResult = null;
        try {
            byte[] bytes = encode(0, address);
            addressResult = com.okexchain.utils.crypto.encode.Bech32.encode(mainPrefix, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return addressResult;
    }

}
