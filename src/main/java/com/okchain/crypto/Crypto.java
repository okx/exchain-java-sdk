package com.okchain.crypto;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.okchain.common.ConstantIF;
import com.okchain.exception.InvalidFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.bitcoinj.crypto.*;
import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.Hex;
import com.okchain.crypto.io.cosmos.util.AddressUtil;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

public class Crypto {
  private static int mnemonicLength = 12;
  private static int mnemonicEntrophyLength = 128;
  private static int privateKeyLength = 32;

  public static String generatePrivateKey() {
    SecureRandom csprng = new SecureRandom();
    byte[] randomBytes = new byte[32];
    csprng.nextBytes(randomBytes);
    return Hex.toHexString(randomBytes);
  }

  public static String generateMnemonic() {
    byte[] entrophy = new byte[mnemonicEntrophyLength / 8];
    new SecureRandom().nextBytes(entrophy);
    try {

      return Utils.join(MnemonicCode.INSTANCE.toMnemonic(entrophy));
    } catch (MnemonicException.MnemonicLengthException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String generatePrivateKeyFromMnemonic(String mnemonic)
      throws InvalidFormatException {
    List<String> words = Splitter.on(" ").splitToList(mnemonic);
    if (words.size() != mnemonicLength)
      throw new InvalidFormatException("mnemonic words should be " + mnemonicLength);
    byte[] seed = MnemonicCode.INSTANCE.toSeed(words, "");
    DeterministicKey key = HDKeyDerivation.createMasterPrivateKey(seed);

    List<ChildNumber> childNumbers = HDUtils.parsePath(ConstantIF.HD_PATH);
    for (ChildNumber cn : childNumbers) {
      key = HDKeyDerivation.deriveChildKey(key, cn);
    }
    return key.getPrivateKeyAsHex();
  }

  public static byte[] sign(byte[] msg, String privateKey)
      throws NoSuchAlgorithmException, InvalidFormatException {
    validatePrivateKey(privateKey);
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

  public static boolean validateSig(byte[] msg, byte[] pubKey, byte[] sig)
      throws NoSuchAlgorithmException {
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

  public static boolean validateSig(byte[] msg, String pubKey, String sig)
      throws NoSuchAlgorithmException {

    return validateSig(msg, Base64.getDecoder().decode(pubKey), Base64.getDecoder().decode(sig));
  }

  public static String generatePubKeyHexFromPriv(String privateKey) {
    validatePrivateKey(privateKey);
    ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
    return k.getPublicKeyAsHex();
  }

  public static byte[] generatePubKeyFromPriv(String privateKey) {
    validatePrivateKey(privateKey);
    ECKey k = ECKey.fromPrivate(new BigInteger(privateKey, 16));
    return k.getPubKey();
  }

  public static String generateAddressFromPriv(String privateKey){
    validatePrivateKey(privateKey);
    String pub = generatePubKeyHexFromPriv(privateKey);
    return generateAddressFromPub(pub);
  }

  public static String generateAddressFromPub(String pubKey) {

    try {
      String addr =
          AddressUtil.createNewAddressSecp256k1(ConstantIF.ADDRESS_PREFIX, Hex.decode(pubKey));
      return addr;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static String generateValidatorAddressFromPub(String pubKey) {

    try {
      String addr =
              AddressUtil.createNewAddressSecp256k1(ConstantIF.VALIDATOR_ADDRESS_PREFIX, Hex.decode(pubKey));
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

  public static void validateAddress(String addr) {
    if (addr == null) throw new InvalidFormatException("null address");
    if (!addr.startsWith(ConstantIF.ADDRESS_PREFIX + "1"))
      throw new InvalidFormatException("invalid Address");
    if (addr.length() != ConstantIF.ADDRESS_PREFIX.length() + 1 + 38)
      throw new InvalidFormatException("invalid Address length");
    try{
      AddressUtil.decodeAddress(addr);
    }catch (Exception e){
      throw new InvalidFormatException("invalid Address");
    }

  }

  public static void validatePrivateKey(String privateKey) {
    byte[] len;
    try{
      len = Hex.decode(privateKey);
    }catch (Exception e){
      throw new InvalidFormatException("invalid privateKey");
    }

    if (privateKey == null || len.length != privateKeyLength)
      throw new InvalidFormatException("invalid privateKey");
  }
}
