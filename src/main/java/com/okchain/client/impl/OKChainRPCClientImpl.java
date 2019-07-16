package com.okchain.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.OKChainClient;
import com.okchain.common.ConstantIF;
import com.okchain.common.HttpUtils;
import com.okchain.common.jsonrpc.JSONRPCUtils;
import com.okchain.crypto.Crypto;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.crypto.keystore.KeyStoreUtils;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OKChainRPCClientImpl implements OKChainClient {
    private String backend;

    private static OKChainRPCClientImpl oKChainRPCClientImpl;

    private OKChainRPCClientImpl(String backend) {
        this.backend = backend;
    }

    public static OKChainRPCClientImpl getOKChainClient(String backend) throws NullPointerException {
        if (oKChainRPCClientImpl == null) {
            oKChainRPCClientImpl = new OKChainRPCClientImpl(backend);
        }
        return oKChainRPCClientImpl;
    }

    private String getAccountPrivate(String userAddress) {
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Address", userAddress);
        byte[] data = JSON.toJSONString(dataMp).getBytes();


//        byte[] addrByte = AddressUtil.decodeAddress(userAddress);
//        byte[] data = new byte[addrByte.length + 1];
//        System.arraycopy(addrByte, 0, data, 1, addrByte.length);
//        data[0] = 0x01;

        Map<String, Object> mp = new TreeMap<>();
        mp.put("data", Hex.toHexString(data).toUpperCase());
        mp.put("height", "0");
        mp.put("path", "custom/acc/account");
        //mp.put("path", "/store/acc/key");
        mp.put("prove", false);
        String res = JSONRPCUtils.call(this.backend, ConstantIF.RPC_METHOD_QUERY, mp);
        JSONObject obj = JSON.parseObject(res);
        String accountBase64 = (String) obj.getJSONObject("result").getJSONObject("response").get("value");
        //System.out.println(accountBase64);
        String accountJson = new String(Base64.decode(accountBase64));
        //System.out.println(accountJson);
        return accountJson;
    }

    public AddressInfo createAddressInfo() {
        String privateKey = Crypto.generatePrivateKey();
        return getAddressInfo(privateKey);
    }

    public AddressInfo getAddressInfo(String privateKey) throws NullPointerException {
        if (privateKey.equals("")) throw new NullPointerException("empty privateKey");
        String pubKey = Crypto.generatePubKeyHexFromPriv(privateKey);
        String address = Crypto.generateAddressFromPub(pubKey);
        return new AddressInfo(privateKey, pubKey, address);
    }

    public JSONObject getAccountFromNode(String userAddress) throws NullPointerException {
        if (userAddress.equals("")) throw new NullPointerException("empty userAddress");
        JSONObject res = JSON.parseObject(getAccountPrivate(userAddress));
        return res;
    }

    private String getSequance(JSONObject account) {
        String res = (String) account.getJSONObject("value").get("sequence");
        return res;
    }

    private String getAccountNumber(JSONObject account) {
        String res = (String) account.getJSONObject("value").get("account_number");
        return res;
    }

    public AccountInfo getAccountInfo(String privateKey) throws NullPointerException {
        if (privateKey.equals("")) throw new NullPointerException("empty privateKey");
        AddressInfo addressInfo = getAddressInfo(privateKey);
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(addressInfo.getUserAddress()));
        String sequence = getSequance(accountJson);
        String accountNumber = getAccountNumber(accountJson);
        return new AccountInfo(addressInfo, accountNumber, sequence);
    }

    public JSONObject sendTransaction(byte[] data) {
        String method = "";

        switch (BuildTransaction.getMode()) {
            case ConstantIF.TX_SEND_MODE_BLOCK:
                method = ConstantIF.RPC_METHOD_TX_SEND_BLOCK;
                break;
            case ConstantIF.TX_SEND_MODE_SYNC:
                method = ConstantIF.RPC_METHOD_TX_SEND_SYNC;
                break;
            case ConstantIF.TX_SEND_MODE_ASYNC:
                method = ConstantIF.RPC_METHOD_TX_SEND_ASYNC;
                break;
            default:
                throw new NullPointerException("invalid TX_SEND_MODE");
        }
        Map<String, Object> mp = new TreeMap<>();
        mp.put("tx", data);
        String res = JSONRPCUtils.call(this.backend, method, mp);
        System.out.println(res);
        JSONObject obj = JSON.parseObject(res).getJSONObject("result");
        return obj;
    }

    // michael.w added 20190715
    public JSONObject sendSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws NullPointerException, IOException {
        checkAccountInfoValue(account);
        if (to.equals("")) throw new NullPointerException("Reciever address is empty.");
        if (amount == null || amount.isEmpty()) throw new NullPointerException("Amount is empty.");
        byte[] data = BuildTransaction.generateAminoSendTransaction(account, to, amount, memo);
        return sendTransaction(data);
    }

    public JSONObject sendSendTransactions(AccountInfo account, List<String> tos, List<List<Token>> amount, String memo) throws NullPointerException, IOException {
        return null;
    }


    public JSONObject sendPlaceOrderTransaction(AccountInfo account, RequestPlaceOrderParams parms, String memo) throws IOException {
        checkAccountInfoValue(account);
        checkPlaceOrderRequestParms(parms);
        byte[] data = BuildTransaction.generateAminoPlaceOrderTransaction(account, parms.getSide(), parms.getProduct(), parms.getPrice(), parms.getQuantity(), memo);
        return sendTransaction(data);
    }

    public JSONObject sendCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws IOException {
        checkAccountInfoValue(account);
        byte[] data = BuildTransaction.generateAminoCancelOrderTransaction(account, orderId, memo);
        return sendTransaction(data);
    }

    public JSONObject sendMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) throws IOException {
        checkAccountInfoValue(account);
        byte[] data = BuildTransaction.generateAminoMultiSendTransaction(account, transfers, memo);
        return sendTransaction(data);

    }

    private void checkAccountInfoValue(AccountInfo account) {
        if (account == null) throw new NullPointerException("AccountInfo is empty.");
        if (account.getAccountNumber().equals("")) throw new NullPointerException("AccountNumber is empty.");
        if (account.getSequenceNumber().equals("")) throw new NullPointerException("SequenceNumber is empty.");
        if (account.getPrivateKey().equals("")) throw new NullPointerException("PrivateKey is empty.");
        if (account.getPublicKey().equals("")) throw new NullPointerException("PublicKey is empty.");
        if (account.getUserAddress().equals("")) throw new NullPointerException("UserAddress is empty.");
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

    public String getPrivateKeyFromMnemonic(String mnemonic) {
        return Crypto.generatePrivateKeyFromMnemonic(mnemonic);
    }

    public String generateMnemonic() {
        return Crypto.generateMnemonic();
    }

    public String generateKeyStore(String privateKey, String passWord) throws CipherException, IOException {
        File file = new File("./");
        return KeyStoreUtils.generateWalletFile(passWord, privateKey, file, true);
    }

    public String getPrivateKeyFromKeyStore(String keyStoreFilePath, String passWord) throws IOException, CipherException {
        return KeyStoreUtils.getPrivateKeyFromKeyStoreFile(keyStoreFilePath, passWord);
    }


    private void checkPlaceOrderRequestParms(RequestPlaceOrderParams parms) {
        if (parms == null) throw new NullPointerException("empty PlaceOrderRequestParms");
        if (parms.getPrice().equals("")) throw new NullPointerException("empty Price");
        if (parms.getProduct().equals("")) throw new NullPointerException("empty Product");
        if (parms.getQuantity().equals("")) throw new NullPointerException("empty Quantity");
        if (parms.getSide().equals("")) throw new NullPointerException("empty Side");
    }

    private BaseModel queryRequest(String url, ArrayList<Pair> pairs) {
        String res = HttpUtils.httpGet(url, pairs);
        return JSON.parseObject(res, BaseModel.class);
    }

    public BaseModel getAccountALLTokens(String address, String show) throws NullPointerException {
        if (address.equals("")) throw new NullPointerException("empty address");
        ArrayList<Pair> pairs = new ArrayList<>();
        if (!show.equals("")) {
            pairs.add(new Pair("show", show));
        }
        return queryRequest(backend + ConstantIF.GET_ACCOUNT_ALL_TOKENS_URL_PATH + address, pairs);
    }

    public BaseModel getAccountToken(String address, String symbol) throws NullPointerException {
        if (address.equals("")) throw new NullPointerException("empty address");
        if (symbol.equals("")) throw new NullPointerException("empty symbol");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("symbol", symbol));
        return queryRequest(backend + ConstantIF.GET_ACCOUNT_TOKEN_URL_PATH + address, pairs);
    }

    public BaseModel getTokens() {
        return queryRequest(backend + ConstantIF.GET_TOKENS_URL_PATH, null);
    }


    public BaseModel getToken(String symbol) throws NullPointerException {
        if (symbol.equals("")) throw new NullPointerException("empty symbol");
        return queryRequest(backend + ConstantIF.GET_TOKEN_URL_PATH + symbol, null);
    }

    public BaseModel getProducts() {
        return queryRequest(backend + ConstantIF.GET_PRODUCTS_URL_PATH, null);
    }

    public BaseModel getDepthBook(String product) throws NullPointerException {
        if (product.equals("")) throw new NullPointerException("empty product");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("product", product));
        return queryRequest(backend + ConstantIF.GET_DEPTHBOOK_URL_PATH, pairs);
    }

    public BaseModel getCandles(String granularity, String instrumentId, String size) throws NullPointerException {
        if (instrumentId.equals("")) throw new NullPointerException("empty product");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("granularity", granularity));
        pairs.add(new Pair("size", size));
        return queryRequest(backend + ConstantIF.GET_CANDLES_URL_PATH + instrumentId, pairs);
    }

    public BaseModel getTickers(String count) {
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("count", count));
        return queryRequest(backend + ConstantIF.GET_TICKERS_URL_PATH, pairs);
    }


    public BaseModel getOrderListOpen(RequestOrderListOpenParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("product", params.getProduct()));
        pairs.add(new Pair("address", params.getAddress()));
        pairs.add(new Pair("side", params.getSide()));
        pairs.add(new Pair("start", params.getStart()));
        pairs.add(new Pair("end", params.getEnd()));
        pairs.add(new Pair("page", params.getPage()));
        pairs.add(new Pair("perPage", params.getPerPage()));
        return queryRequest(backend + ConstantIF.GET_ORDERLIST_OPEN_URL_PATH, pairs);

    }

    public BaseModel getOrderListClosed(RequestOrderListClosedParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("product", params.getProduct()));
        pairs.add(new Pair("address", params.getAddress()));
        pairs.add(new Pair("side", params.getSide()));
        pairs.add(new Pair("start", params.getStart()));
        pairs.add(new Pair("end", params.getEnd()));
        pairs.add(new Pair("page", params.getPage()));
        pairs.add(new Pair("perPage", params.getPerPage()));
        pairs.add(new Pair("hideNoFill", params.getHideNoFill()));
        return queryRequest(backend + ConstantIF.GET_ORDERLIST_CLOSED_URL_PATH, pairs);
    }

    public BaseModel getDeals(RequestDealsParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("product", params.getProduct()));
        pairs.add(new Pair("address", params.getAddress()));
        pairs.add(new Pair("side", params.getSide()));
        pairs.add(new Pair("start", params.getStart()));
        pairs.add(new Pair("end", params.getEnd()));
        pairs.add(new Pair("page", params.getPage()));
        pairs.add(new Pair("perPage", params.getPerPage()));
        return queryRequest(backend + ConstantIF.GET_DEALS_URL_PATH, pairs);
    }

    public BaseModel getTransactions(RequestTransactionsParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("address", params.getAddress()));
        pairs.add(new Pair("type", params.getType()));
        pairs.add(new Pair("start", params.getStart()));
        pairs.add(new Pair("end", params.getEnd()));
        pairs.add(new Pair("page", params.getPage()));
        pairs.add(new Pair("perPage", params.getPerPage()));
        return queryRequest(backend + ConstantIF.GET_TRANSACTIONS_URL_PATH, pairs);
    }

}
