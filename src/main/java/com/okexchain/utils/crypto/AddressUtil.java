package com.okexchain.utils.crypto;

import com.okexchain.env.EnvInstance;
import com.okexchain.utils.crypto.encode.Bech32;
import com.okexchain.utils.crypto.encode.ConvertBits;
import com.okexchain.utils.crypto.hash.Ripemd;
import com.okexchain.utils.exception.AddressFormatException;
import lombok.extern.slf4j.Slf4j;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static org.bitcoinj.core.ECKey.CURVE;

@Slf4j
public class AddressUtil {

    public static String createNewAddressSecp256k1(String mainPrefix, byte[] publickKey){
        // convert 33 bytes public key to 65 bytes public key

        String addressResult = null;
        try {
            byte[] uncompressedPubKey = CURVE.getCurve().decodePoint(publickKey).getEncoded(false);
            byte[] pub = new byte[64];
            // truncate last 64 bytes to generate address
            System.arraycopy(uncompressedPubKey, 1, pub, 0, 64);

            //get address
            byte[] address = Keys.getAddress(pub);
            //get okexchain
            byte[] bytes = encode(0, address);
            addressResult = com.okexchain.utils.crypto.encode.Bech32.encode(mainPrefix, bytes);
        } catch (Exception e) {

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

    public static String convertAddressFromHexToBech32(String hexAddress){
        byte[] address = Numeric.hexStringToByteArray(hexAddress);

        String bech32Address = null;
        try {
            byte[] bytes = encode(0, address);
            bech32Address = Bech32.encode(EnvInstance.getEnv().GetMainPrefix(), bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bech32Address;
    }

    public static String convertAddressFromBech32ToHex(String bech32Address){
        String hexAddress = null;
        try {
            byte[] bytes = decodeAddress(bech32Address);
            hexAddress = Numeric.toHexString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Keys.toChecksumAddress(hexAddress);
    }

    public static String convertAddressFromValToBech32(String valAddress){
        String bech32Address = null;
        try {
            byte[] bytes = Bech32.decode(valAddress).getData();
            bech32Address = com.okexchain.utils.crypto.encode.Bech32.encode(EnvInstance.getEnv().GetMainPrefix(), bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bech32Address;
    }

    public static String convertAddressFromBech32ToVal(String bech32Address){
        String valAddress = null;
        try {
            byte[] bytes = Bech32.decode(bech32Address).getData();
            valAddress = com.okexchain.utils.crypto.encode.Bech32.encode(EnvInstance.getEnv().GetValidatorAddrPrefix(), bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return valAddress;
    }
}
