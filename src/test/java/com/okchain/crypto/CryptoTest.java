package com.okchain.crypto;

import com.okchain.crypto.keystore.CipherException;
import com.okchain.crypto.keystore.KeyStoreUtils;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import com.okchain.crypto.io.cosmos.util.AddressUtil;
import java.util.Base64;

public class CryptoTest {
    private static String TEST_PRIVATE_KEY = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
    private static String TEST_MNEMONIC = "total lottery arena when pudding best candy until army spoil drill pool";

    @Test
    public void generatePrivateKeyTest() {
        String priv = Crypto.generatePrivateKey();
        Assert.assertNotEquals(TEST_PRIVATE_KEY,priv);
    }

    @Test
    public void generateAddressTest() {
        long startTime = System.currentTimeMillis();
        byte[] pub = Crypto.generatePubKeyFromPriv(TEST_PRIVATE_KEY);
        Assert.assertEquals("02fa42bcdb80aae828e480d80ec05c3a8847a2dc150071d08adf2f99fbcb09a3fb",Hex.toHexString(pub));
        try {
            String addr = AddressUtil.createNewAddressSecp256k1("okchain", pub);
            Assert.assertEquals("okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf",addr);
        }catch (Exception e){
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime) / 1000;
        System.out.println("execution time：" + excTime + "s");

    }

    @Test
    public void verifyTest() {
        String priv = Crypto.generatePrivateKey();
        byte[] msg = new String("hello").getBytes();
        try {
            byte[] sig = Crypto.sign(msg, priv);
            String sigBase64 = Base64.getEncoder().encodeToString(sig);
            byte[] pub = Crypto.generatePubKeyFromPriv(priv);
            boolean res = Crypto.validateSig(msg, Base64.getEncoder().encodeToString(pub), sigBase64);
            Assert.assertEquals(true, res);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void generateMnemonicTest() {
        Assert.assertNotEquals(TEST_MNEMONIC,Crypto.generateMnemonic());
    }

    @Test
    public void generatePrivateKeyFromMnemonicTest() {
        String mnemonic = "total lottery arena when pudding best candy until army spoil drill pool";
        String priv = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
        Assert.assertEquals("29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884",priv);
        Assert.assertEquals("okchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptdqket5k",Crypto.generateAddressFromPriv(priv));
    }


    @Test
    public void generateKeyStoreTest() {
        File file = new File("./");
        String fileName = "";
        try {
            fileName = KeyStoreUtils.generateNewWalletFile("1234567", file);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
        File keyStoreFile = new File(fileName);
        keyStoreFile.delete();
    }

    @Test
    public void getPrivatekeyFromKeyStoreTest() {
        File file = new File("./");
        String password = "1234567";
        String filename = "";
        try {
            filename = KeyStoreUtils.generateWalletFile(password, TEST_PRIVATE_KEY, file, true);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String privateKey = KeyStoreUtils.getPrivateKeyFromKeyStoreFile(filename, password);
            Assert.assertEquals(TEST_PRIVATE_KEY,privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        file.delete();
        File keyStoreFile = new File(filename);
        keyStoreFile.delete();
    }


    @Test
    public void validPubKeyTest() {
        String priv = Crypto.generatePrivateKey();
        String pub = Crypto.generatePubKeyHexFromPriv(priv);
        Assert.assertTrue(Crypto.validPubKey(pub));
        String pub2 = "3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f3f";
        Assert.assertTrue(Crypto.validPubKey(pub2));
        String pub3 = "3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F";
        Assert.assertTrue(Crypto.validPubKey(pub3));
        String pub4 = "";
        Assert.assertFalse(Crypto.validPubKey(pub4));
        String pub5 = null;
        Assert.assertFalse(Crypto.validPubKey(pub5));
        String pub6 = "3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3f";
        Assert.assertTrue(Crypto.validPubKey(pub6));
        String pub7 = "3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3F3f3f";
        Assert.assertFalse(Crypto.validPubKey(pub7));
    }

}
