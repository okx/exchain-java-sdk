package com.okchain.types;

public class AddressInfo {
    private String privateKey;
    private String publicKey;
    private String userAddress;

    public AddressInfo(String privateKey, String publicKey, String userAddress) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.userAddress = userAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
