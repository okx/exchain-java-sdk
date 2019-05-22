package io.okchain.client;

import io.okchain.http.OKChainRequest;

public class OKChainClient {
    public String privateKey;
    public String userAddress;
    public String accountNumber;
    public String chainId;
    public String serverUrl;

    public OKChainClient(String privateKey, String userAddress, String serverUrl) {
        this.privateKey = privateKey;
        this.userAddress = userAddress;
        this.chainId = Args.chainId;
        this.serverUrl = serverUrl;
        this.accountNumber = OKChainRequest.GetAccountNumber(this);
    }

    public OKChainClient(String privateKey, String userAddress, int accountNumber) {
        this.privateKey = privateKey;
        this.userAddress = userAddress;
        this.accountNumber = accountNumber+"";
        this.chainId = Args.chainId;
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

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
