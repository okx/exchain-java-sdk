package io.cosmos.crypto;

import com.google.common.base.Splitter;
import io.cosmos.common.EnvInstance;
import io.cosmos.util.AddressUtil;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.*;
import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;


public class Crypto {

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

    public static String generatePrivateKey() {
        SecureRandom csprng = new SecureRandom();
        byte[] randomBytes = new byte[32];
        csprng.nextBytes(randomBytes);
        return Hex.toHexString(randomBytes);
    }

    public static String generatePubKeyHexFromPriv(String privateKey) {
        ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
        return k.getPublicKeyAsHex();
    }

    public static String generateMnemonic() {
        byte[] entrophy = new byte[128/4];
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

        List<ChildNumber> childNumbers = HDUtils.parsePath(EnvInstance.getEnv().GetHDPath());
        for (ChildNumber cn : childNumbers) {
            key = HDKeyDerivation.deriveChildKey(key, cn);
        }
        return key.getPrivateKeyAsHex();
    }

    public static boolean validateSig(byte[] msg, byte[] pubKey, byte[] sig) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] msgHash = digest.digest(msg);

        byte[] buf = new byte[32];
        System.arraycopy(sig, 0, buf, 0, 32);
        BigInteger r = new BigInteger(1, buf);
        System.arraycopy(sig, 32, buf, 0, 32);
        BigInteger s = new BigInteger(1, buf);
        ECKey.ECDSASignature signature = new ECKey.ECDSASignature(r, s);
        return ECKey.verify(msgHash, signature, pubKey);
    }

    public static boolean validateSig(byte[] msg, String pubKey, String sig) throws NoSuchAlgorithmException {
        return validateSig(msg, Base64.getDecoder().decode(pubKey), Base64.getDecoder().decode(sig));
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
            String addr = AddressUtil.createNewAddressSecp256k1(EnvInstance.getEnv().GetMainPrefix(), Hex.decode(pubKey));
            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean validPubKey(String pubKey) {
        if (pubKey == null || pubKey.length() != 66) {
            return false;
        }
        try {
            Hex.decode(pubKey);
        } catch (DecoderException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public static String generateValidatorAddressFromPub(String pubKey) {

        try {
            String addr = AddressUtil.createNewAddressSecp256k1(EnvInstance.getEnv().GetValidatorAddrPrefix(), Hex.decode(pubKey));
            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
