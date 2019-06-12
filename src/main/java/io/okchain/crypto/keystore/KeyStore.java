package io.okchain.crypto.keystore;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import static io.okchain.crypto.Crypto.generateAddressFromPriv;
import static io.okchain.crypto.keystore.SecureRandomUtils.secureRandom;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Taken from Ethereum web3j and redevelop it
 * https://github.com/web3j/web3j/blob/master/core/src/main/java/org/web3j/crypto/Wallet.java
 */
public class KeyStore {

    private static final int N_LIGHT = 1 << 12;
    private static final int P_LIGHT = 6;

    private static final int N_STANDARD = 1 << 18;
    private static final int P_STANDARD = 1;

    private static final int R = 8;
    private static final int DKLEN = 32;

    private static final int CURRENT_VERSION = 3;

    private static final String CIPHER = "aes-128-ctr";
    static final String AES_128_CTR = "pbkdf2";
    static final String SCRYPT = "scrypt";

    public static KeyStoreFile create(String password, String privateKey, int n, int p)
            throws CipherException {

        byte[] salt = generateRandomBytes(32);

        byte[] derivedKey = generateDerivedScryptKey(
                password.getBytes(UTF_8), salt, n, R, p, DKLEN);

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
        byte[] iv = generateRandomBytes(16);

        byte[] privateKeyBytes = Hex.decode(privateKey);

        byte[] cipherText = performCipherOperation(
                    Cipher.ENCRYPT_MODE, iv, encryptKey, privateKeyBytes);

        byte[] mac = generateMac(derivedKey, cipherText);

        return createWalletFile(privateKey, cipherText, iv, salt, mac, n, p);
    }

    public static KeyStoreFile createStandard(String password, String privateKey)
            throws CipherException {
        return create(password, privateKey, N_STANDARD, P_STANDARD);
    }

    public static KeyStoreFile createLight(String password, String privateKey)
            throws CipherException {
        return create(password, privateKey, N_LIGHT, P_LIGHT);
    }

    private static KeyStoreFile createWalletFile(
            String privateKey, byte[] cipherText, byte[] iv, byte[] salt, byte[] mac,
            int n, int p) {

        KeyStoreFile walletFile = new KeyStoreFile();
        walletFile.setAddress(generateAddressFromPriv(privateKey));

        KeyStoreFile.Crypto crypto = new KeyStoreFile.Crypto();
        crypto.setCipher(CIPHER);
        crypto.setCiphertext(Hex.toHexString(cipherText));

        KeyStoreFile.CipherParams cipherParams = new KeyStoreFile.CipherParams();
        cipherParams.setIv(Hex.toHexString(iv));
        crypto.setCipherparams(cipherParams);

        crypto.setKdf(SCRYPT);
        KeyStoreFile.ScryptKdfParams kdfParams = new KeyStoreFile.ScryptKdfParams();
        kdfParams.setDklen(DKLEN);
        kdfParams.setN(n);
        kdfParams.setP(p);
        kdfParams.setR(R);
        kdfParams.setSalt(Hex.toHexString(salt));
        crypto.setKdfparams(kdfParams);

        crypto.setMac(Hex.toHexString(mac));
        walletFile.setCrypto(crypto);
        walletFile.setId(UUID.randomUUID().toString());
        walletFile.setVersion(CURRENT_VERSION);

        return walletFile;
    }

    private static byte[] generateDerivedScryptKey(
            byte[] password, byte[] salt, int n, int r, int p, int dkLen) throws CipherException {
        return SCrypt.generate(password, salt, n, r, p, dkLen);
    }

    private static byte[] generateAes128CtrDerivedKey(
            byte[] password, byte[] salt, int c, String prf) throws CipherException {

        if (!prf.equals("hmac-sha256")) {
            throw new CipherException("Unsupported prf:" + prf);
        }

        // Java 8 supports this, but you have to convert the password to a character array, see
        // http://stackoverflow.com/a/27928435/3211687

        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        gen.init(password, salt, c);
        return ((KeyParameter) gen.generateDerivedParameters(256)).getKey();
    }

    private static byte[] performCipherOperation(
            int mode, byte[] iv, byte[] encryptKey, byte[] text) throws CipherException {

        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
            cipher.init(mode, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(text);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException
                | BadPaddingException | IllegalBlockSizeException e) {
            throw new CipherException("Error performing cipher operation", e);
        }
    }

    private static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
        byte[] result = new byte[16 + cipherText.length];

        System.arraycopy(derivedKey, 16, result, 0, 16);
        System.arraycopy(cipherText, 0, result, 16, cipherText.length);
        return sha3(result);
    }

    public static byte[] sha3(byte[] input, int offset, int length) {
        Keccak.DigestKeccak kecc = new Keccak.Digest256();
        kecc.update(input, offset, length);
        return kecc.digest();
    }

    public static byte[] sha3(byte[] input) {
        return sha3(input, 0, input.length);
    }

    public static String decrypt(String password, KeyStoreFile walletFile)
            throws CipherException {

        validate(walletFile);

        KeyStoreFile.Crypto crypto = walletFile.getCrypto();

        byte[] mac = Hex.decode(crypto.getMac());
        byte[] iv = Hex.decode(crypto.getCipherparams().getIv());
        byte[] cipherText = Hex.decode(crypto.getCiphertext());

        byte[] derivedKey;

        KeyStoreFile.KdfParams kdfParams = crypto.getKdfparams();
        if (kdfParams instanceof KeyStoreFile.ScryptKdfParams) {
            KeyStoreFile.ScryptKdfParams scryptKdfParams =
                    (KeyStoreFile.ScryptKdfParams) crypto.getKdfparams();
            int dklen = scryptKdfParams.getDklen();
            int n = scryptKdfParams.getN();
            int p = scryptKdfParams.getP();
            int r = scryptKdfParams.getR();
            byte[] salt = Hex.decode(scryptKdfParams.getSalt());
            derivedKey = generateDerivedScryptKey(password.getBytes(UTF_8), salt, n, r, p, dklen);
        } else if (kdfParams instanceof KeyStoreFile.Aes128CtrKdfParams) {
            KeyStoreFile.Aes128CtrKdfParams aes128CtrKdfParams =
                    (KeyStoreFile.Aes128CtrKdfParams) crypto.getKdfparams();
            int c = aes128CtrKdfParams.getC();
            String prf = aes128CtrKdfParams.getPrf();
            byte[] salt = Hex.decode(aes128CtrKdfParams.getSalt());

            derivedKey = generateAes128CtrDerivedKey(password.getBytes(UTF_8), salt, c, prf);
        } else {
            throw new CipherException("Unable to deserialize params: " + crypto.getKdf());
        }

        byte[] derivedMac = generateMac(derivedKey, cipherText);

        if (!Arrays.equals(derivedMac, mac)) {
            throw new CipherException("Invalid password provided");
        }

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
        byte[] privateKey = performCipherOperation(Cipher.DECRYPT_MODE, iv, encryptKey, cipherText);
    return Hex.toHexString(privateKey);
    }

    static void validate(KeyStoreFile walletFile) throws CipherException {
        KeyStoreFile.Crypto crypto = walletFile.getCrypto();

        if (walletFile.getVersion() != CURRENT_VERSION) {
            throw new CipherException("Wallet version is not supported");
        }

        if (!crypto.getCipher().equals(CIPHER)) {
            throw new CipherException("Wallet cipher is not supported");
        }

        if (!crypto.getKdf().equals(AES_128_CTR) && !crypto.getKdf().equals(SCRYPT)) {
            throw new CipherException("KDF type is not supported");
        }
    }

    static byte[] generateRandomBytes(int size) {
        byte[] bytes = new byte[size];
        secureRandom().nextBytes(bytes);
        return bytes;
    }
}
