package com.okchain.crypto.keystore;

/**
 * Cipher exception wrapper.
 * Taken from Ethereum web3j
 * https://github.com/web3j/web3j/blob/master/crypto/src/main/java/org/web3j/crypto/CipherException.java
 */
public class CipherException extends Exception {

    public CipherException(String message) {
        super(message);
    }

    public CipherException(Throwable cause) {
        super(cause);
    }

    public CipherException(String message, Throwable cause) {
        super(message, cause);
    }
}
