package com.okexchain.sample;

import com.okexchain.utils.crypto.AddressUtil;
import com.okexchain.utils.crypto.Crypto;
import com.okexchain.utils.crypto.PrivateKey;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;
import java.math.BigInteger;
import java.security.Key;
import java.security.SignatureException;

public class EthAddress {

    public static void main(String[] args) throws SignatureException, Exception {

        TestGeneratePrivateKeyFromMnemonic();
//        BigInteger privKey = new BigInteger(
//                "8FC2AD0591940F1FC0412166E373E927AA4E6E137BFB37A6DAEAB7E4309FF4D5", 16);
//
//        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
//        ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
//
//        String address = Keys.getAddress(pubKey);
//        String okAddress = AddressUtil.createNewAddressEthSecp256k1("okexchain", Hex.decode(address));
//        System.out.println("okAddress: " + okAddress);
//
//        String msg = "Message for signing";
//        byte[] msgHash = Hash.sha3(msg.getBytes());
//        Sign.SignatureData signature =
//                Sign.signMessage(msgHash, keyPair, false);
//
//        System.out.println("Msg: " + msg);
//        System.out.println("Msg hash: " + Hex.toHexString(msgHash));
//        System.out.printf(
//                "Signature: [v = %d, r = %s, s = %s]\n",
//                signature.getV() - 27,
//                Hex.toHexString(signature.getR()),
//                Hex.toHexString(signature.getS()));
//
//        BigInteger pubKeyRecovered =
//                Sign.signedMessageToKey(msg.getBytes(), signature);
//        System.out.println("Recovered public key: " +
//                pubKeyRecovered.toString(16));
//
//        boolean validSig = pubKey.equals(pubKeyRecovered);
//        System.out.println("Signature valid? " + validSig);

    }

    public static String compressPubKey(BigInteger pubKey) {
        String pubKeyYPrefix = pubKey.testBit(0) ? "03" : "02";
        String pubKeyHex = pubKey.toString(16);
        String pubKeyX = pubKeyHex.substring(0, 64);
        return pubKeyYPrefix + pubKeyX;
    }

    public static void TestCreateNewAddressEthSecp256k1() throws Exception {
        PrivateKey key = new PrivateKey( "8FC2AD0591940F1FC0412166E373E927AA4E6E137BFB37A6DAEAB7E4309FF4D5");
        String priKey = key.getPriKey();
        String addr = AddressUtil.createNewAddressEthSecp256k1("okexchain", Hex.decode(priKey));
        System.out.println("addr is: " + addr);
    }

    public static void TestGeneratePrivateKeyFromMnemonic() throws Exception {

        String pri = Crypto.generatePrivateKeyFromMnemonic("raw flame junior chunk mule together aerobic water motor crunch sense alien");

        System.out.println("private: " + pri);
        String addr = AddressUtil.createNewAddressEthSecp256k1("okexchain", Hex.decode(pri));
        System.out.println("addr is: " + addr);

    }
}
