package io.okchain.crypto;

import io.okchain.crypto.keystore.CipherException;
import io.okchain.crypto.keystore.KeyStoreUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class CryptoTest {
    @Test
    public void testGeneratePrivateKey() {
        String priv = Crypto.generatePrivateKey();
        System.out.println(priv);
    }

    @Test
    public void testGenerateAddress() {
        String priv = Crypto.generatePrivateKey();
        long startTime = System.currentTimeMillis();
        byte[] pub = Crypto.generatePubKeyFromPriv(priv);
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
    public void generateMnemonic() {
        System.out.println(Crypto.generateMnemonic());
    }

    @Test
    public void generatePrivateKeyFromMnemonic() {
        String mnemonic = "total lottery arena when pudding best candy until army spoil drill pool";
        System.out.println(Crypto.generatePrivateKeyFromMnemonic(mnemonic));
    }

    @Test
    public void generateKeyStore() {
        File file = new File("./");
        try {
            KeyStoreUtils.generateNewWalletFile("jilei", file);
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
    }

    @Test
    public void getPrivatekeyFromKeyStore() {
        File file = new File("./");
        String password = "jilei";
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


}
