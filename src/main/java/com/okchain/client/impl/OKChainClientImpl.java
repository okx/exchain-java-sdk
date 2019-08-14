package com.okchain.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.OKChainClient;
import com.okchain.common.ConstantIF;
import com.okchain.common.HttpUtils;
import com.okchain.crypto.Crypto;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.crypto.keystore.KeyStoreUtils;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        System.out.println(url);
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

    public AddressInfo getAddressInfo(String privateKey) throws NullPointerException {
        if (privateKey.equals("")) throw new NullPointerException("empty privateKey");
        String pubKey = Crypto.generatePubKeyHexFromPriv(privateKey);
        String address = Crypto.generateAddressFromPub(pubKey);
        return new AddressInfo(privateKey, pubKey, address);
    }

    public AccountInfo getAccountInfo(String privateKey) throws NullPointerException {
        if (privateKey.equals("")) throw new NullPointerException("empty privateKey");
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

    public JSONObject sendSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws NullPointerException {
        // check whether the account is empty
        checkAccountInfoValue(account);
        // check the receiver addr is empty
        if (to.equals("")) throw new NullPointerException("empty to");
        if (amount == null || amount.isEmpty()) throw new NullPointerException("empty amount");
        // generate the final
        // 生成最终要发送到网络中的json串(String)
        String data = BuildTransaction.generateSendTransaction(account, to, amount, memo);
        // sendTransaction是用post请求将data(json串发到主网)
        return sendTransaction(data);
    }


    // michael.w added 20190708
    public JSONObject sendSendTransactions(AccountInfo account, List<String> tos, List<List<Token>>
            amounts, String memo) throws NullPointerException {
        if (tos.size() != amounts.size()) {
            System.out.println("the lengths of reciever addresses and amount are not the same");
            return null;
        }
        // 检查这个账户不是空账户
        checkAccountInfoValue(account);
        // 检查收款地址不是空
        for (int i = 0; i < tos.size(); i++) {
            if (tos.get(i).equals("")) throw new NullPointerException("empty to");
        }
        // 检查转账金额是否为空
        for (int i = 0; i < amounts.size(); i++) {
            List<Token> amount = amounts.get(i);
            if (amount == null || amount.isEmpty()) throw new NullPointerException("empty amount");
        }
        // 生成最终要发送到网络中的json串(String)
        String data = BuildTransaction.generateSendTransactions(account, tos, amounts, memo);
        // sendTransaction是用post请求将data(json串发到主网)
        System.out.println(data);
        return sendTransaction(data);
        // return 主网给的答复：
        // string{"height":"8539","txhash":"022BF9F1F67831843158596EACB72752F9E72EB6456B0B07A5183B7A597FF72E","raw_log":"[{\"msg_index\":\"0\",\"success\":true,\"log\":\"\"}]","logs":[{"msg_index":"0","success":true,"log":""}],"tags":[{"key":"fee","value":"0.01250000 okb"},{"key":"action","value":"send"}]}
    }


    public JSONObject sendPlaceOrderTransaction(AccountInfo account, RequestPlaceOrderParams parms, String memo) throws NullPointerException {
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

    public JSONObject sendMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) {
        checkAccountInfoValue(account);
        if (transfers == null || transfers.isEmpty()) throw new NullPointerException("empty transfers");
        String data = BuildTransaction.generateMultiSendTransaction(account, transfers, memo);
        return sendTransaction(data);
    }

    private JSONObject sendTransaction(String data) {
        String res = HttpUtils.httpPost(this.backend + ConstantIF.TRANSACTION_URL_PATH, data);
        // System.out.println("post back string"+res);
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

    public BaseModel getMatches(String product, String start, String end, String page, String perPage) throws NullPointerException {
        if (product.equals("")) throw new NullPointerException("empty product");
        ArrayList<Pair> pairs = new ArrayList<>();
        pairs.add(new Pair("start", start));
        pairs.add(new Pair("end", end));
        pairs.add(new Pair("page", page));
        pairs.add(new Pair("perPage", perPage));
        return queryRequest(backend + ConstantIF.GET_MATCHES_URL_PATH, pairs);
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
