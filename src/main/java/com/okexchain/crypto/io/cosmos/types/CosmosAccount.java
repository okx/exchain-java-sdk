package com.okexchain.crypto.io.cosmos.types;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-24 09:16
 **/
public class CosmosAccount {

    private String address;

    private String accountNumber;

    private String sequence;

    private String chainPath;

    public CosmosAccount(String address, String accountNumber, String sequence, String chainPath) {
        this.address = address;
        this.accountNumber = accountNumber;
        this.sequence = sequence;
        this.chainPath = chainPath;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getSequence() {
        return sequence;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getChainPath() {
        return chainPath;
    }

    public void setChainPath(String chainPath) {
        this.chainPath = chainPath;
    }
}
