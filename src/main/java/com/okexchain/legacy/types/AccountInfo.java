package com.okexchain.legacy.types;

public class AccountInfo extends AddressInfo {


    private String accountNumber;
    private String sequenceNumber;

    public AccountInfo(String privateKey, String publicKey, String userAddress, String accountNumber, String sequenceNumber) {
        super(privateKey, publicKey, userAddress);
        this.accountNumber = accountNumber;
        this.sequenceNumber = sequenceNumber;
    }

    public AccountInfo(AddressInfo addressInfo, String accountNumber, String sequenceNumber) {
        super(addressInfo.getPrivateKey(), addressInfo.getPublicKey(), addressInfo.getUserAddress());
        this.accountNumber = accountNumber;
        this.sequenceNumber = sequenceNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "privateKey='" + super.getPrivateKey() + "\', " +
                "publicKey='" + super.getPublicKey() + "\', " +
                "address='" + super.getUserAddress() + "\', " +
                "accountNumber='" + accountNumber + "\', " +
                "sequenceNumber='" + sequenceNumber + "\'" +
                '}';
    }
}
