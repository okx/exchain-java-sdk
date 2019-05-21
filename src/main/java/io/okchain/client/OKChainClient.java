package io.okchain.client;

public class OKChainClient {
    public String privateKey;
    public String userAddress;
    public String accountNumber;
    public String chainId;

    public OKChainClient(String privateKey, String userAddress, String accountNumber, String chainId) {
        this.privateKey = privateKey;
        this.userAddress = userAddress;
        this.accountNumber = accountNumber;
        this.chainId = chainId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }
}
