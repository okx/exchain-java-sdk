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
import com.okchain.types.staking.CommissionRates;
import com.okchain.types.staking.Description;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OKChainRestClientImpl implements OKChainClient {

    private String backend;

    private static OKChainRestClientImpl okChainClient;

    private OKChainRestClientImpl(String backend) {
        this.backend = backend;
    }

    public static OKChainClient getOKChainClient(String backend) throws NullPointerException {
        if (okChainClient == null) {
            okChainClient = new OKChainRestClientImpl(backend);
        }
        return okChainClient;
    }
    // 通过ok addr，访问主网，返回有关该地址的一些属性信息
    // 通过http的get方法访问主网，拿到关于该地址的一些信息，包括公钥、account_number和sequence(json串)
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

    public AccountInfo createAccount() {
        return new AccountInfo(createAddressInfo(), "0", "0");
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
        //System.out.println(getAccountPrivate(addressInfo.getUserAddress()));

        // 将json串转为对象
        // getAccountPrivate方法利用ok地址通过http的get方法访问主网，拿到关于该地址的一些信息，包括公钥、account_number和sequence(json串)
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(addressInfo.getUserAddress()));

        String sequence = getSequance(accountJson);
        String accountNumber = getAccountNumber(accountJson);
//        String sequence = "1";
//        String accountNumber = "0";

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
    public AccountInfo getAccountInfoFromMnemonic(String mnemo) throws NullPointerException {
        if (mnemo.equals("")) throw new NullPointerException("empty mnemonic");
        return getAccountInfo(getPrivateKeyFromMnemonic(mnemo));
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
        // 检查这个账户不是空账户
        checkAccountInfoValue(account);

        // 检查收款地址不是空
        if (to.equals(""))
            throw new NullPointerException("empty to");

        if (amount == null || amount.isEmpty())
            throw new NullPointerException("empty amount");
        // 生成最终要发送到网络中的json串(String)
        String data = BuildTransaction.generateSendTransaction(account, to, amount, memo);
        // sendTransaction是用post请求将data(json串发到主网)
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

    public JSONObject sendMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) throws IOException {
        checkAccountInfoValue(account);
        if (transfers == null || transfers.isEmpty()) throw new NullPointerException("empty transfers");
        String data = BuildTransaction.generateMultiSendTransaction(account, transfers, memo);
        return sendTransaction(data);
    }


    public JSONObject sendCreateValidatorTransaction(AccountInfo account, Description description, CommissionRates commission, Token minSelfDelegation,
                                                 String delegatorAddress, String validatorAddress, String pubKey, String memo) throws NullPointerException {
        checkAccountInfoValue(account);

        String data = BuildTransaction.generateCreateValidatorTransaction(account, description,
                commission, minSelfDelegation, delegatorAddress, validatorAddress, pubKey, memo);
        System.out.println(data);
        return sendTransaction(data);
    }

    public JSONObject sendEditValidatorTransaction(AccountInfo account, String minSelfDelegation, String validatorAddress,
                                                   Description description, String memo) throws NullPointerException {
        checkAccountInfoValue(account);

        String data = BuildTransaction.generateEditValidatorTransaction(account, minSelfDelegation, validatorAddress, description, memo);
        System.out.println(data);
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
        pairs.add(new Pair("per_page", params.getPerPage()));
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
        pairs.add(new Pair("hide_no_fill", params.getHideNoFill()));
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
        pairs.add(new Pair("per_page", params.getPerPage()));
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
        pairs.add(new Pair("per_page", params.getPerPage()));
        return queryRequest(backend + ConstantIF.GET_TRANSACTIONS_URL_PATH, pairs);
    }

    public BaseModel getMatches(String product, String start, String end, String page, String perPage) throws NullPointerException {
        return new BaseModel();
    }

    public String getTickersV2(String instrumentId) {
        return "";
    }

    public String getInstrumentsV2() {
        return "";
    }

    public String getOrderListOpenV2(String instrument_id, String after, String before, int limit) throws NullPointerException {
        return "";
    }

    public String getOrderV2(String order_id) {
        return "";
    }

    public JSONObject sendPlaceOrderTransactionV2(AccountInfo account, RequestPlaceOrderParams parms, String memo) throws NullPointerException, IOException {
        return JSON.parseObject("{}");
    }

    public JSONObject sendCancelOrderTransactionV2(AccountInfo account, String orderId, String memo) throws NullPointerException, IOException {
        return JSON.parseObject("{}");
    }

}
