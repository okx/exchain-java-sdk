package com.okexchain.utils.crypto;

import com.okexchain.utils.crypto.encode.Bech32;
import com.okexchain.utils.crypto.encode.ConvertBits;
import com.okexchain.utils.exception.AddressFormatException;
import org.web3j.crypto.Keys;
import org.web3j.utils.Numeric;

public class AddressConvertUtil {

    private static final String okexchain = "okexchain";
    private static final String ex = "ex";

    // nothing to do with prefix
    public static String convertFromBech32ToHex(String address) {
        return convertAddressFromBech32ToHex(address);
    }

    public static String convertFromHexToOkexchainBech32(String address) {
        return convertAddressFromHexToBech32(okexchain, address);
    }

    public static String convertFromHexToExBech32(String address) {
        return convertAddressFromHexToBech32(ex, address);
    }

    public static String convertFromExBech32ToOkexchainBech32(String address) {
        String hexAddress = convertAddressFromBech32ToHex(address);
        return convertAddressFromHexToBech32(okexchain, hexAddress);
    }

    public static String convertFromOkexchainBech32ToExBech32(String address) {
        String hexAddress = convertAddressFromBech32ToHex(address);
        return convertAddressFromHexToBech32(ex, hexAddress);
    }

    public static String convertFromOkexchainValToExVal(String address) {
        String hexAddress = convertAddressFromBech32ToHex(address);
        return convertAddressFromHexToBech32(ex+"valoper", hexAddress);
    }

    public static String convertAddressFromHexToBech32(String prefix, String hexAddress) {
        byte[] address = Numeric.hexStringToByteArray(hexAddress);

        String bech32Address = null;
        try {
            byte[] bytes = encode(0, address);
            bech32Address = Bech32.encode(prefix, bytes);
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

    public static String convertAddressFromValToBech32(String prefix, String valAddress){
        String bech32Address = null;
        try {
            byte[] bytes = Bech32.decode(valAddress).getData();
            bech32Address = Bech32.encode(prefix, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bech32Address;
    }

    public static String convertAddressFromBech32ToVal(String prefix, String bech32Address){
        String valAddress = null;
        try {
            byte[] bytes = Bech32.decode(bech32Address).getData();
            valAddress = Bech32.encode(prefix, bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return valAddress;
    }


    private static byte[] decodeAddress(String address){
        byte[] dec = Bech32.decode(address).getData();
        return ConvertBits.convertBits(dec, 0, dec.length, 5, 8, false);
    }


    private static byte[] encode(int witnessVersion, byte[] witnessProgram) throws AddressFormatException {
        byte[] convertedProgram = ConvertBits.convertBits(witnessProgram, 0, witnessProgram.length, 8, 5, true);
        return convertedProgram;
    }


}
