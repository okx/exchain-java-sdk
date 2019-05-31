package io.okchain.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.crypto.tink.subtle.Hex;
import io.okchain.client.OKChainClient;
import io.okchain.common.ConstantIF;
import io.okchain.common.HttpUtils;
import io.okchain.crypto.AddressUtil;
import io.okchain.crypto.Crypto;
import io.okchain.transaction.BuildTransaction;
import io.okchain.types.AccountInfo;
import io.okchain.types.AddressInfo;
import io.okchain.types.PlaceOrderRequestParms;
import io.okchain.types.Token;

import java.util.List;

public class OKChainClientImpl implements OKChainClient {

    private String backend;

    private static OKChainClientImpl okChainClient;

    private OKChainClientImpl(String backend) {
        this.backend = backend;
    }

    public static OKChainClient getOKChainClient(String backend) throws NullPointerException {
        if (okChainClient == null) {
            okChainClient = new OKChainClientImpl(backend);
        }
        return okChainClient;
    }


    private String getAccountPrivate(String userAddress) {
        String url = backend + ConstantIF.ACCOUNT_URL_PATH + userAddress;
        return HttpUtils.httpGet(url);

    }

    public JSONObject getAccountFromNode(String userAddress) throws NullPointerException {
        if (userAddress.equals("")) throw new NullPointerException("empty userAddress");
        JSONObject res = JSON.parseObject(getAccountPrivate(userAddress));
        return res;
    }

    public AddressInfo createAddressInfo() {
        String privateKey = Crypto.generatePrivateKey();
        return getAddressInfo(privateKey);
    }

    private AddressInfo getAddressInfo(String privateKey) throws NullPointerException {
        if (privateKey.equals("")) throw new NullPointerException("empty prvateKey");
        String pubKey = Crypto.generatePubKeyHexFromPriv(privateKey);
        String address = "";
        try {
            address = AddressUtil.createNewAddressSecp256k1(ConstantIF.ADDRESS_PREFIX, Hex.decode(pubKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AddressInfo(privateKey, pubKey, address);
    }

    public AccountInfo getAccountInfo(String privateKey) throws NullPointerException {
        if (privateKey.equals("")) throw new NullPointerException("empty prvateKey");
        AddressInfo addressInfo = getAddressInfo(privateKey);
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(addressInfo.getUserAddress()));
        String sequence = getSequance(accountJson);
        String accountNumber = getAccountNumber(accountJson);
        return new AccountInfo(addressInfo, accountNumber, sequence);
    }

    private String getSequance(JSONObject account) {
        String res = (String) account.getJSONObject("value").get("sequence");
        return res;
    }

    private String getAccountNumber(JSONObject account) {
        String res = (String) account.getJSONObject("value").get("account_number");
        return res;
    }

    public JSONObject sendSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws NullPointerException {
        checkAccountInfoValue(account);
        if (to.equals("")) throw new NullPointerException("empty to");
        if (amount == null || amount.isEmpty()) throw new NullPointerException("empty amount");

        String data = BuildTransaction.generateSendTransaction(account, to, amount, memo);
        return sendTransaction(data);
    }


    public JSONObject sendPlaceOrderTransaction(AccountInfo account, PlaceOrderRequestParms parms, String memo) throws NullPointerException {
        checkAccountInfoValue(account);
        checkPlaceOrderRequestParms(parms);
        String data = BuildTransaction.generatePlaceOrderTransaction(account, parms.getSide(), parms.getProduct(), parms.getPrice(), parms.getQuantity(), memo);
        return sendTransaction(data);
    }


    public JSONObject sendCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws NullPointerException {
        checkAccountInfoValue(account);
        if (orderId.equals("")) throw new NullPointerException("empty orderId");

        String data = BuildTransaction.generateCancelOrderTransaction(account, orderId, memo);
        return sendTransaction(data);
    }

    private JSONObject sendTransaction(String data) {
        String res = HttpUtils.httpPost(this.backend + ConstantIF.TRANSACTION_URL_PATH, data);
        return JSON.parseObject(res);
    }

    private void checkAccountInfoValue(AccountInfo account) {
        if (account == null) throw new NullPointerException("empty AccountInfo");
        if (account.getAccountNumber().equals("")) throw new NullPointerException("empty accountNumber");
        if (account.getSequenceNumber().equals("")) throw new NullPointerException("empty SequenceNumber");
        if (account.getPrivateKey().equals("")) throw new NullPointerException("empty PrivateKey");
        if (account.getPublicKey().equals("")) throw new NullPointerException("empty PublicKey");
        if (account.getUserAddress().equals("")) throw new NullPointerException("empty UserAddress");
        //        if (account.getPrivateKey().equals("")) return false;
        //        if (account.getSequenceNumber().equals("") || account.getAccountNumber().equals("")) {
        //            JSONObject accountJson = JSON.parseObject(account.getPrivateKey());
        //            String sequence = getSequance(accountJson);
        //            String accountNumber = getAccountNumber(accountJson);
        //            if (sequence.equals("") || accountNumber.equals("")) return false;
        //            if (account.getSequenceNumber().equals("")) account.setSequenceNumber(sequence);
        //            if (account.getAccountNumber().equals(""))
        // account.setAccountNumber(accountNumber);
        //        }
        //        return true;
        //    }
    }

    private void checkPlaceOrderRequestParms(PlaceOrderRequestParms parms) {
        if (parms == null) throw new NullPointerException("empty PlaceOrderRequestParms");
        if (parms.getPrice().equals("")) throw new NullPointerException("empty Price");
        if (parms.getProduct().equals("")) throw new NullPointerException("empty Product");
        if (parms.getQuantity().equals("")) throw new NullPointerException("empty Quantity");
        if (parms.getSide().equals("")) throw new NullPointerException("empty Side");
    }

}
