package com.okchain.crypto;


import com.google.common.base.Splitter;
import com.okchain.common.ConstantIF;
import org.bitcoin.NativeSecp256k1Util;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.*;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


/**
 * @program: coin-parent-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-20 19:29
 **/
public class Crypto {

    public static String generatePrivateKey() {
        byte[] privateKey = new byte[32];
        new SecureRandom().nextBytes(privateKey);
        return Hex.toHexString(privateKey);

    }

    public static String generateMnemonic() {
        byte[] entrophy = new byte[128 / 8];
        new SecureRandom().nextBytes(entrophy);
        try {

            return Utils.join(MnemonicCode.INSTANCE.toMnemonic(entrophy));
        } catch (MnemonicException.MnemonicLengthException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generatePrivateKeyFromMnemonic(String mnemonic) {
        List<String> words = Splitter.on(" ").splitToList(mnemonic);
        byte[] seed = MnemonicCode.INSTANCE.toSeed(words, "");
        DeterministicKey key = HDKeyDerivation.createMasterPrivateKey(seed);

        List<ChildNumber> childNumbers = HDUtils.parsePath(ConstantIF.HD_PATH);
        for (ChildNumber cn : childNumbers) {
            key = HDKeyDerivation.deriveChildKey(key, cn);
        }
        return key.getPrivateKeyAsHex();
    }


    public static byte[] sign(byte[] msg, String privateKey) throws NoSuchAlgorithmException {
        ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));

        return sign(msg, k);
    }

    public static byte[] sign(byte[] msg, ECKey k) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] msgHash = digest.digest(msg);

        ECKey.ECDSASignature signature = k.sign(Sha256Hash.wrap(msgHash));

        byte[] result = new byte[64];
        System.arraycopy(Utils.bigIntegerToBytes(signature.r, 32), 0, result, 0, 32);
        System.arraycopy(Utils.bigIntegerToBytes(signature.s, 32), 0, result, 32, 32);
        return result;
    }

    public static boolean validateSig(byte[] msg, String pubKey, String sig) throws NoSuchAlgorithmException, NativeSecp256k1Util.AssertFailException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] msgHash = digest.digest(msg);

        byte[] sigBytes = Base64.getDecoder().decode(sig);
        byte[] buf = new byte[32];
        System.arraycopy(sigBytes, 0, buf, 0, 32);
        BigInteger r = new BigInteger(buf);
        System.arraycopy(sigBytes, 32, buf, 0, 32);
        BigInteger s = new BigInteger(buf);
        ECKey.ECDSASignature signature = new ECKey.ECDSASignature(r, s);

        byte[] pubBytes = Base64.getDecoder().decode(pubKey);
        return ECKey.verify(msgHash, signature, pubBytes);
    }

    public static String generatePubKeyHexFromPriv(String privateKey) {
        ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        return k.getPublicKeyAsHex();
    }

    public static byte[] generatePubKeyFromPriv(String privateKey) {
        ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        return k.getPubKey();
    }

    public static String generateAddressFromPriv(String privateKey) {
        String pub = generatePubKeyHexFromPriv(privateKey);
        return generateAddressFromPub(pub);
    }

    public static String generateAddressFromPub(String pubKey) {
        try {
            String addr = AddressUtil.createNewAddressSecp256k1(ConstantIF.ADDRESS_PREFIX, Hex.decode(pubKey));
            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
