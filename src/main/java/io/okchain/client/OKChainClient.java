package io.okchain.client;

import io.cosmos.crypto.Crypto;
import io.cosmos.types.Token;
import io.cosmos.util.AddressUtil;
import io.okchain.api.transaction.BuildTransaction;
import io.okchain.http.OKChainRequest;

import java.util.List;

public class OKChainClient {
    public String privateKey;
    public String userAddress;
    public String accountNumber;
    public String chainId;
    public String serverUrl;

    public OKChainClient(String serverUrl) {
        this.privateKey = Crypto.generatePrivateKey();
        this.createAddress();
        this.serverUrl = serverUrl;
    }

    public OKChainClient(String privateKey, String serverUrl) {
        this.privateKey = privateKey;
        this.createAddress();
        this.chainId = Args.chainId;
        this.serverUrl = serverUrl;
        this.accountNumber = OKChainRequest.GetAccountNumber(this);

    }

    public OKChainClient(String privateKey, int accountNumber) {
        this.privateKey = privateKey;
        this.accountNumber = accountNumber+"";
        this.chainId = Args.chainId;
        this.createAddress();
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

    private void createAddress() {
        byte[] pub = Crypto.generatePubkeyFromPriv(this.privateKey);
        try {
            String addr = AddressUtil.createNewAddressSecp256k1(Args.addressPrefix,pub);
            this.userAddress = addr;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getAccount() {
        return OKChainRequest.GetAccount(this);
    }

    public String sendTransferTransaction(String to, List<Token> amount, String memo, String sequence) {
        String data = BuildTransaction.generateSendTransaction(this,to,amount,memo,sequence);
        return OKChainRequest.SendTransaction(this,data);
    }

    public String sendTransferTransaction(String to, List<Token> amount, String memo) {
        String data = BuildTransaction.generateSendTransaction(this,to,amount,memo);
        return OKChainRequest.SendTransaction(this,data);
    }

    public String sendPlaceOrderTransaction(String side, String product, String price, String quantity, String memo, String sequence) {
        String data = BuildTransaction.generatePlaceOrderTransaction(this,side,product,price,quantity,memo,sequence);
        return OKChainRequest.SendTransaction(this,data);
    }

    public String sendPlaceOrderTransaction(String side, String product, String price, String quantity, String memo) {
        String data = BuildTransaction.generatePlaceOrderTransaction(this,side,product,price,quantity,memo);
        return OKChainRequest.SendTransaction(this,data);
    }

    public String sendCancelOrderTransaction(String orderId, String memo, String sequence) {
        String data = BuildTransaction.generateCancelOrderTransaction(this,orderId,memo,sequence);
        return OKChainRequest.SendTransaction(this,data);
    }

    public String sendCancelOrderTransaction(String orderId, String memo) {
        String data = BuildTransaction.generateCancelOrderTransaction(this,orderId,memo);
        return OKChainRequest.SendTransaction(this,data);
    }
}
