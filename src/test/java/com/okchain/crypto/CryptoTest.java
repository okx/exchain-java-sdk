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
    @Test
    public void testGeneratePrivateKey() {
        String priv = Crypto.generatePrivateKey();
        System.out.println(priv);
    }

    @Test
    public void testGenerateAddress() {
//        String priv = Crypto.generatePrivateKey();
        String priv = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
        long startTime = System.currentTimeMillis();
        byte[] pub = Crypto.generatePubKeyFromPriv(priv);
        System.out.println(Hex.toHexString(pub));
        try {
            String addr = AddressUtil.createNewAddressSecp256k1("okchain", pub);
            System.out.println(addr);
        }catch (Exception e){
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime) / 1000;
        System.out.println("执行时间：" + excTime + "s");

    }

    @Test
    public void testVerify() {
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
    public void generateMnemonic() {
        System.out.println(Crypto.generateMnemonic());
    }

    @Test
    public void generatePrivateKeyFromMnemonic() {
        String mnemonic = "total lottery arena when pudding best candy until army spoil drill pool";
        String priv = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
        System.out.println(priv);
        System.out.println(Crypto.generateAddressFromPriv(priv));
    }


    @Test
    public void generateKeyStore() {
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
    public void getPrivatekeyFromKeyStore() {
        File file = new File("./");
        String password = "1234567";
        String filename = "";
        try {
            filename = KeyStoreUtils.generateWalletFile(password, "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632", file, true);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String privateKey = KeyStoreUtils.getPrivateKeyFromKeyStoreFile(filename, password);
            System.out.println(privateKey);
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
    public void validPubKey() {
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
