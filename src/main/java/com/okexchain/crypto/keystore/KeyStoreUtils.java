package com.okexchain.crypto.keystore;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okexchain.crypto.Crypto;
import com.okexchain.exception.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class KeyStoreUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final SecureRandom secureRandom = SecureRandomUtils.secureRandom();
    private static final int PRIVATE_KEY_LENGTH_IN_HEX = 64;

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String generateFullNewWalletFile(String password, File destinationDirectory)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {

        return generateNewWalletFile(password, destinationDirectory, true);
    }

    public static String generateLightNewWalletFile(String password, File destinationDirectory)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {

        return generateNewWalletFile(password, destinationDirectory, false);
    }

    public static String generateNewWalletFile(String password, File destinationDirectory)
            throws CipherException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return generateFullNewWalletFile(password, destinationDirectory);
    }

    public static String generateNewWalletFile(
            String password, File destinationDirectory, boolean useFullScrypt)
            throws CipherException, IOException {

        String privateKey = Crypto.generatePrivateKey();
        return generateWalletFile(password, privateKey, destinationDirectory, useFullScrypt);
    }

    public static String generateWalletFile(
            String password, String privateKey, File destinationDirectory, boolean useFullScrypt)
            throws CipherException, IOException {

        KeyStoreFile walletFile;
        if (useFullScrypt) {
            walletFile = KeyStore.createStandard(password, privateKey);
        } else {
            walletFile = KeyStore.createLight(password, privateKey);
        }

        String fileName = getWalletFileName(walletFile);
        File destination = new File(destinationDirectory, fileName);

        objectMapper.writeValue(destination, walletFile);

        return fileName;
    }

    private static String getWalletFileName(KeyStoreFile walletFile) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(
                "'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return now.format(format) + walletFile.getAddress() + ".txt";
    }

    public static String getPrivateKeyFromKeyStoreFile(String keyStorePath, String passWord) throws IOException, CipherException {
        File file = new File(keyStorePath);
        if (!file.exists()){
            throw new InvalidFormatException("keyStorePath is not exist");
        }
        KeyStoreFile keyStoreFile = objectMapper.readValue(file, KeyStoreFile.class);
        String privatetKey = KeyStore.decrypt(passWord, keyStoreFile);
        return privatetKey;
    }

    public static String getPrivateKeyFromKeyStore(String keyStore, String passWord) throws IOException, CipherException {
        KeyStoreFile keyStoreFile = objectMapper.readValue(keyStore, KeyStoreFile.class);
        String privatetKey = KeyStore.decrypt(passWord, keyStoreFile);
        return privatetKey;
    }

}
