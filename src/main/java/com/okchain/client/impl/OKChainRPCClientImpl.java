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
        JSONObject accountJson = getAccountFromNode(addressInfo.getUserAddress());
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
        JSONObject obj = JSON.parseObject(res).getJSONObject("result");
        return obj;
    }

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

    public JSONObject sendPlaceOrderTransaction(AccountInfo account, RequestPlaceOrderParams params, String memo) throws IOException {
        checkAccountInfoValue(account);
        checkPlaceOrderRequestParms(params);
        byte[] data = BuildTransaction.generateAminoPlaceOrderTransaction(account, params.getSide(), params.getProduct(), params.getPrice(), params.getQuantity(), memo);
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

    // convert type JSONObject 2 type BaseModel
    private BaseModel JSONObject2BaseModel(JSONObject jo) {
        JSONObject extractJSONObject = jo.getJSONObject("result").getJSONObject("response");
        BaseModel bm = new BaseModel();

        if (extractJSONObject.containsKey("code")) {
            // if the rpc query fails
            bm.setCode(extractJSONObject.getString("code"));
            bm.setDetailMsg(extractJSONObject.getString("log"));
        } else {
            // if the rpc query succeeds
            bm.setCode("0");
            bm.setData(new String(Base64.decode((String) extractJSONObject.get("value"))));
        }
        return bm;
    }

    // get the part from the return of function ABCIQuery
    private JSONObject extractObject(JSONObject jo) {
        JSONObject extractJSONObject = jo.getJSONObject("result").getJSONObject("response");
        if (extractJSONObject.containsKey("code")) {
            // if the rpc query fails
            return extractJSONObject;
        } else {
            // if the rpc query succeeds
            byte[] bz = Base64.decode((String) extractJSONObject.get("value"));
            return JSON.parseObject(new String(bz));
        }

    }


    private JSONObject ABCIQuery(String path, byte[] data, String rpcMethod) {
        Map<String, Object> mp = new TreeMap<>();
        if (data == null) {
            mp.put("data", null);
        } else {
            mp.put("data", Hex.toHexString(data).toUpperCase());
        }
        mp.put("height", "0");
        mp.put("path", path);
        mp.put("prove", false);
        String res = JSONRPCUtils.call(this.backend, rpcMethod, mp);
        return JSON.parseObject(res);
    }


    public JSONObject getAccountFromNode(String addr) throws NullPointerException {
        if (addr.equals("")) throw new NullPointerException("empty userAddress");
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Address", addr);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        String path = "custom/acc/account";
        return extractObject(ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY));
    }

    public BaseModel getAccountALLTokens(String addr, String show) throws NullPointerException {
        if (addr.equals("")) throw new NullPointerException("empty address");
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("symbol", "");
        dataMp.put("show", show);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        String path = "custom/token/accounts/" + addr;
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }


    public BaseModel getAccountToken(String addr, String symbol) throws NullPointerException {
        if (addr.equals("")) throw new NullPointerException("empty address");
        if (symbol.equals("")) throw new NullPointerException("empty symbol");
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("symbol", symbol);
        dataMp.put("show", "partial");
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        String path = "custom/token/accounts/" + addr;
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }

    public BaseModel getTokens() {
        String path = "custom/token/tokens";
        JSONObject jo = ABCIQuery(path, null, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }


    public BaseModel getToken(String symbol) throws NullPointerException {
        if (symbol.equals("")) throw new NullPointerException("empty symbol");
        String path = "custom/token/info/" + symbol;
        JSONObject jo = ABCIQuery(path, null, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }

    public BaseModel getProducts() {
        String path = "custom/token/products";
        JSONObject jo = ABCIQuery(path, null, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }

    public BaseModel getDepthBook(String product) throws NullPointerException {
        if (product.equals("")) throw new NullPointerException("empty product");
        String path = "custom/order/depthbook";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", product);
        // default size is 200
        dataMp.put("Size", "200");
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);

    }

    public BaseModel getCandles(String granularity, String instrumentId, String size) throws NullPointerException {
        if (instrumentId.equals("")) throw new NullPointerException("empty product");
        String path = "custom/backend/candles";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", instrumentId);
        dataMp.put("Granularity", granularity);
        dataMp.put("Size", size);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);

    }

    public BaseModel getTickers(String count) {
        String path = "custom/backend/tickers";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", "");
        dataMp.put("Count", count);
        dataMp.put("Sort", true);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }

    // get the info of the product's record of transaction
    public BaseModel getMatches(String product, String start, String end, String page, String perPage) throws NullPointerException {
        if (product.equals("")) throw new NullPointerException("empty product");
        String path = "custom/backend/matches";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", product);
        dataMp.put("Start", start);
        dataMp.put("End", end);
        dataMp.put("Page", page);
        dataMp.put("PerPage", perPage);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);

    }

    public BaseModel getOrderListOpen(RequestOrderListOpenParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        String path = "custom/backend/orders/open";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Address", params.getAddress());
        dataMp.put("Product", params.getProduct());
        dataMp.put("Page", params.getPage());
        dataMp.put("PerPage", params.getPerPage());
        dataMp.put("Start", params.getStart());
        dataMp.put("End", params.getEnd());
        dataMp.put("Side", params.getSide());
        dataMp.put("HideNoFill", false);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }

    public BaseModel getOrderListClosed(RequestOrderListClosedParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        String path = "custom/backend/orders/closed";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", params.getProduct());
        dataMp.put("Address", params.getAddress());
        dataMp.put("Side", params.getSide());
        dataMp.put("Page", params.getPage());
        dataMp.put("PerPage", params.getPerPage());
        dataMp.put("Start", params.getStart());
        dataMp.put("End", params.getEnd());
        if (params.getHideNoFill() == "true") {
            dataMp.put("HideNoFill", true);
        } else {
            dataMp.put("HideNoFill", false);
        }
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }

    public BaseModel getDeals(RequestDealsParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        String path = "custom/backend/deals";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Address", params.getAddress());
        dataMp.put("Product", params.getProduct());
        dataMp.put("Page", params.getPage());
        dataMp.put("PerPage", params.getPerPage());
        dataMp.put("Start", params.getStart());
        dataMp.put("End", params.getEnd());
        dataMp.put("Side", params.getSide());
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);

    }

    public BaseModel getTransactions(RequestTransactionsParams params) throws NullPointerException {
        if (params.getAddress().equals("")) throw new NullPointerException("empty Address");
        String path = "custom/backend/deals";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Address", params.getAddress());
        dataMp.put("Type", params.getType());
        dataMp.put("End", params.getEnd());
        dataMp.put("Page", params.getPage());
        dataMp.put("PerPage", params.getPerPage());
        dataMp.put("Start", params.getStart());
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject2BaseModel(jo);
    }

}
