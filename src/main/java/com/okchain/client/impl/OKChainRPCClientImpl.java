package com.okchain.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.gson.JsonObject;
import com.okchain.client.OKChainClient;
import com.okchain.common.ConstantIF;
import com.okchain.common.StrUtils;
import com.okchain.common.jsonrpc.JSONRPCUtils;
import com.okchain.crypto.Crypto;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.crypto.keystore.KeyStoreUtils;
import com.okchain.exception.InvalidFormatException;
import com.okchain.exception.OKChainException;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


import java.io.File;
import java.io.IOException;
import java.util.*;

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

    // account

    private String getSequance(JSONObject account) {
        return (String) account.getJSONObject("value").get("sequence");
    }

    private String getAccountNumber(JSONObject account) {
        return (String) account.getJSONObject("value").get("account_number");
    }

    public String getPrivateKeyFromMnemonic(String mnemonic) {
        return Crypto.generatePrivateKeyFromMnemonic(mnemonic);
    }

    public AddressInfo createAddressInfo() {
        return getAddressInfo(Crypto.generatePrivateKey());
    }

    public AddressInfo getAddressInfo(String privateKey) throws NullPointerException {
        Crypto.validatePrivateKey(privateKey);
        String pubKey = Crypto.generatePubKeyHexFromPriv(privateKey);
        String address = Crypto.generateAddressFromPub(pubKey);
        return new AddressInfo(privateKey, pubKey, address);
    }

    public AccountInfo createAccount() {
        return new AccountInfo(createAddressInfo(), "0", "0");
    }

    public AccountInfo getAccountInfo(String privateKey) throws NullPointerException {
        AddressInfo addressInfo = getAddressInfo(privateKey);
        JSONObject accountJson = getAccountFromNode(addressInfo.getUserAddress());
        System.out.println(accountJson);
        String sequence;
        String accountNumber;
        try{
            sequence = getSequance(accountJson);
            accountNumber = getAccountNumber(accountJson);
        }catch (Exception e){
            //System.out.println(e.getMessage());
            throw new OKChainException("account not exist on OKChain");
        }

        return new AccountInfo(addressInfo, accountNumber, sequence);
    }

    public AccountInfo getAccountInfoFromMnemonic(String mnemo) throws NullPointerException {
        if (mnemo.equals("")) throw new NullPointerException("empty mnemonic");
        return getAccountInfo(getPrivateKeyFromMnemonic(mnemo));
    }

    public String generateMnemonic() {
        return Crypto.generateMnemonic();
    }

    public String generateKeyStore(String privateKey, String passWord) throws CipherException, IOException {
        if(passWord.length() > 30) {
            throw new InvalidFormatException("length of password need <= 30");
        }
        Crypto.validatePrivateKey(privateKey);
        File file = new File("./");
        return KeyStoreUtils.generateWalletFile(passWord, privateKey, file, true);
    }

    public String getPrivateKeyFromKeyStore(String keyStoreFilePath, String passWord) throws IOException, CipherException {
        if(passWord.length() > 30) {
            throw new InvalidFormatException("length of password need <= 30");
        }
        return KeyStoreUtils.getPrivateKeyFromKeyStoreFile(keyStoreFilePath, passWord);
    }
    // transact

    private void checkAccountInfoValue(AccountInfo account) {
        if (account == null) throw new NullPointerException("AccountInfo is empty.");
        if (account.getAccountNumber()==null||account.getAccountNumber().equals("")) throw new NullPointerException("AccountNumber is empty.");
        if (account.getSequenceNumber()==null||account.getSequenceNumber().equals("")) throw new NullPointerException("SequenceNumber is empty.");
        if (account.getPrivateKey()==null||account.getPrivateKey().equals("")) throw new NullPointerException("PrivateKey is empty.");
        if (account.getPublicKey()==null||account.getPublicKey().equals("")) throw new NullPointerException("PublicKey is empty.");
        if (account.getUserAddress()==null||account.getUserAddress().equals("")) throw new NullPointerException("UserAddress is empty.");

        Crypto.validateAddress(account.getUserAddress());
        Crypto.validatePrivateKey(account.getPrivateKey());
        if (!Crypto.validPubKey(account.getPublicKey())) throw new InvalidFormatException("invalid pubkey");
        if (!StrUtils.isNumeric(account.getAccountNumber())) throw new InvalidFormatException("invalid accountNumber");
        if (!StrUtils.isNumeric(account.getSequenceNumber())) throw new InvalidFormatException("invalid sequenceNumber");

    }

    private JSONObject sendTransaction(byte[] data) {
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
        //System.out.println(obj);
        JSONObject resObj = new JSONObject();
        JSONObject execObj;
        resObj.put("txhash", obj.getString("hash"));
        if (method.equals(ConstantIF.RPC_METHOD_TX_SEND_BLOCK)) {
            resObj.put("height", obj.getIntValue("height"));
            if (obj.getJSONObject("check_tx").containsKey("code")&&obj.getJSONObject("check_tx").getIntValue("code")!=0) {
                System.out.println("yes");
                execObj = obj.getJSONObject("check_tx");
            }else {
                execObj = obj.getJSONObject("deliver_tx");
            }
            //System.out.println(execObj);
            if (execObj.containsKey("log")) {
                String rawLog = execObj.getString("log");
                //System.out.println(rawLog);
                resObj.put("raw_log", rawLog);
                try{
                    JSONArray logObj = JSONObject.parseArray(rawLog);
                    resObj.put("logs", logObj);
                }catch (Exception e){
                    try{
                        JSONObject logObj = JSONObject.parseObject(rawLog);
                        JSONArray arr = new JSONArray();
                        arr.add(logObj);
                        resObj.put("logs", arr);
                        //System.out.println(arr);
                    }catch (Exception e2){
                        resObj.put("logs", rawLog);
                    }

                }


            }
            if (execObj.containsKey("code")) {
                resObj.put("code", execObj.get("code"));
            }

            if (execObj.containsKey("data")) {
                if (execObj.get("data") == null) resObj.put("data", null);
                else resObj.put("data", Hex.toHexString(execObj.getBytes("data")));
            }
            if (execObj.containsKey("info")) {
                resObj.put("info", execObj.get("info"));
            }
            if (execObj.containsKey("tags")) {
                JSONArray tags = execObj.getJSONArray("tags");
                JSONArray otags = new JSONArray();
                int i;
                for (i=0;i<tags.size();i++){
                    JSONObject tag = new JSONObject();
                    tag.put("key", new String(tags.getJSONObject(i).getBytes("key")));
                    tag.put("value", new String(tags.getJSONObject(i).getBytes("value")));
                    otags.add(tag);
                }
                resObj.put("tags", otags);
            }
            if (execObj.containsKey("events")) {
                JSONArray events = execObj.getJSONArray("events");
                JSONArray newEvents = new JSONArray();
                for (int i=0;i<events.size();i++) {
                    JSONObject event = events.getJSONObject(i);
                    JSONObject newEvent = new JSONObject();
                    //System.out.println(event);
                    newEvent.put("type", event.getString("type"));
                    JSONArray attributes = event.getJSONArray("attributes");
                    JSONArray newAttributes = new JSONArray();
                    newEvent.put("attributes", newAttributes);
                    for (int j=0;j<attributes.size();j++) {
                        JSONObject attribute = attributes.getJSONObject(j);
                        JSONObject newAttribute = new JSONObject();
                        newAttribute.put("key", new String(Base64.decode(attribute.getString("key"))));
                        newAttribute.put("value", new String(Base64.decode(attribute.getString("value"))));
                        newAttributes.add(newAttribute);
                    }
                    newEvents.add(newEvent);
                }
                resObj.put("events", newEvents);
            }
            if (execObj.containsKey("codespace")) {
                resObj.put("codespace", execObj.get("codespace"));
            }
        }else {
            if (obj.containsKey("log")) {
                String rawLog = obj.getString("log");

                resObj.put("raw_log", rawLog);
                try{
                    JSONArray logObj = JSONObject.parseArray(rawLog);
                    resObj.put("logs", logObj);
                }catch (Exception e){
                    try{
                        JSONObject logObj = JSONObject.parseObject(rawLog);
                        JSONArray arr = new JSONArray();
                        arr.add(logObj);
                        resObj.put("logs", arr);
                        //System.out.println(arr);
                    }catch (Exception e2){
                        resObj.put("logs", rawLog);
                    }

                }
            }
            if (obj.containsKey("code")) {
                resObj.put("code", obj.get("code"));
            }
            if (obj.containsKey("data")) {
                resObj.put("data", Hex.toHexString(obj.getBytes("data")));
            }
        }

        return resObj;
    }

    private void checkPlaceOrderRequestParms(RequestPlaceOrderParams parms) {
        if (parms == null) throw new NullPointerException("empty PlaceOrderRequestParms");
        if (parms.getPrice()==null||parms.getPrice().equals("")) throw new NullPointerException("empty Price");
        if (parms.getProduct()==null||parms.getProduct().equals("")) throw new NullPointerException("empty Product");
        if (parms.getQuantity()==null||parms.getQuantity().equals("")) throw new NullPointerException("empty Quantity");
        if (parms.getSide()==null||parms.getSide().equals("")) throw new NullPointerException("empty Side");

        if (!StrUtils.isDecimal(parms.getPrice(), ConstantIF.DECIMAL_N)) throw new InvalidFormatException("invalid price, need " + ConstantIF.DECIMAL_N +" decimals after .");
        if (!StrUtils.isDecimal(parms.getQuantity(), ConstantIF.DECIMAL_N)) throw new InvalidFormatException("invalid quantity, need " + ConstantIF.DECIMAL_N +" decimals after .");
        if (!StrUtils.isProduct(parms.getProduct())) throw new InvalidFormatException("invalid product");
        if (!StrUtils.isProductSide(parms.getSide())) throw new InvalidFormatException("invalid product side");
    }

    public JSONObject sendSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws NullPointerException, IOException {
        checkAccountInfoValue(account);
        Crypto.validateAddress(to);
        if (amount ==null) throw new NullPointerException("amount should not be null");
        if (amount.isEmpty()) throw new NullPointerException("Amount is empty.");
        Iterator<Token> it=amount.iterator();
        while(it.hasNext()) {
            Token t=it.next();
            if (!StrUtils.isDecimal(t.getAmount(), ConstantIF.DECIMAL_N)) throw new InvalidFormatException("invalid amount, need " + ConstantIF.DECIMAL_N +" decimals after .");
        }

        byte[] data = BuildTransaction.generateAminoSendTransaction(account, to, amount, memo);
        System.out.println(Hex.toHexString(data));
        return sendTransaction(data);
    }

    public JSONObject sendPlaceOrderTransaction(AccountInfo account, RequestPlaceOrderParams params, String memo) throws IOException {
        checkAccountInfoValue(account);
        checkPlaceOrderRequestParms(params);
        byte[] data = BuildTransaction.generateAminoPlaceOrderTransaction(account, params.getSide(), params.getProduct(), params.getPrice(), params.getQuantity(), memo);
        return sendTransaction(data);
    }

    public JSONObject sendCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws IOException {
        checkAccountInfoValue(account);
        if (orderId==null||orderId=="") throw new InvalidFormatException("empty orderId");
        if (orderId.length()>30) throw new InvalidFormatException("the length of orderId is too long");
        byte[] data = BuildTransaction.generateAminoCancelOrderTransaction(account, orderId, memo);
        return sendTransaction(data);
    }

    // query
    // convert type JSONObject 2 type BaseModel
    private BaseModel queryJO2BM(JSONObject jo) {
        JSONObject extractJSONObject = jo.getJSONObject("result").getJSONObject("response");
        BaseModel bm = new BaseModel();
        if (extractJSONObject.containsKey("code") && extractJSONObject.getIntValue("code") != 0) {
            // if the rpc query fails
            bm.setCode(extractJSONObject.getIntValue("code"));
            bm.setDetailMsg(extractJSONObject.getString("log"));
        } else {
            // if the rpc query succeeds
            bm.setCode(0);
            bm.setData(new String(Base64.decode((String) extractJSONObject.get("value"))));
        }
        return bm;
    }

    // get the part from the return of function ABCIQuery
    private JSONObject extractObject(JSONObject jo) {
        //System.out.println("xxx");
        //System.out.println(jo);
        JSONObject extractJSONObject = jo.getJSONObject("result").getJSONObject("response");
        if (extractJSONObject.containsKey("code")&&extractJSONObject.getIntValue("code")!=0) {
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
        if (addr == null || addr.equals("")) throw new NullPointerException("empty userAddress");
        Crypto.validateAddress(addr);
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Address", addr);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        String path = "custom/acc/account";
        return extractObject(ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY));
    }

    public BaseModel getAccountALLTokens(String addr, String show) throws NullPointerException {
        if (addr==null||addr.equals("")) throw new NullPointerException("empty address");
        Crypto.validateAddress(addr);
        if (!show.equals("all") && !show.equals("partial") && !show.equals("")) throw new InvalidFormatException("invalid show param,should be 'all' or 'partial'");
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("symbol", "");
        dataMp.put("show", show);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        String path = "custom/token/accounts/" + addr;
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel getAccountToken(String addr, String symbol) throws NullPointerException {
        Crypto.validateAddress(addr);
        if (addr.equals("")) throw new NullPointerException("empty address");
        if (symbol.equals("")) throw new NullPointerException("empty symbol");

        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("symbol", symbol);
        dataMp.put("show", "partial");
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        String path = "custom/token/accounts/" + addr;
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel getTokens() {
        String path = "custom/token/tokens";
        JSONObject jo = ABCIQuery(path, null, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel getToken(String symbol) throws NullPointerException {
        if (symbol==null||symbol.equals("")) throw new NullPointerException("empty symbol");
        String path = "custom/token/info/" + symbol;
        JSONObject jo = ABCIQuery(path, null, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel getProducts() {
        String path = "custom/token/products";
        JSONObject jo = ABCIQuery(path, null, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel getDepthBook(String product) throws NullPointerException {
        if (!StrUtils.isProduct(product)) throw new InvalidFormatException("invalid product");
        if (product.equals("")) throw new NullPointerException("empty product");
        String path = "custom/order/depthbook";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", product);
        // default size is 200
        dataMp.put("Size", "200");
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);

    }

    public BaseModel getCandles(String granularity, String product, String size) throws NullPointerException {
        if (size!=""&&!StrUtils.isNumeric(size)) throw new InvalidFormatException("invalid size");
        if (granularity!=""&&!StrUtils.isNumeric(granularity)) throw new InvalidFormatException("invalid granularity");
        if (!StrUtils.isProduct(product)) throw new InvalidFormatException("invalid product");

        String path = "custom/backend/candles";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", product);
        dataMp.put("Granularity", granularity);
        dataMp.put("Size", size);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);

    }

    public BaseModel getTickers(String count) {
        if (count!=""&&!StrUtils.isNumeric(count)) throw new InvalidFormatException("invalid count");

        String path = "custom/backend/tickers";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", "");
        dataMp.put("Count", count);
        dataMp.put("Sort", true);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    private void validateStartEndPagePerPage(String start, String end, String page, String perPage) {
        if (start!=""&&!StrUtils.isNumeric(start)) throw new InvalidFormatException("invalid start");
        if (end!=""&&!StrUtils.isNumeric(end)) throw new InvalidFormatException("invalid end");
        if (page!=""&&!StrUtils.isNumeric(page)) throw new InvalidFormatException("invalid page");
        if (perPage!=""&&!StrUtils.isNumeric(perPage)) throw new InvalidFormatException("invalid perPage");
    }
    // get the info of the product's record of transaction
    public BaseModel getMatches(String product, String start, String end, String page, String perPage) throws NullPointerException {
        if (!StrUtils.isProduct(product)) throw new InvalidFormatException("invalid product");
        validateStartEndPagePerPage(start,end,page,perPage);


        String path = "custom/backend/matches";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", product);
        dataMp.put("Start", start);
        dataMp.put("End", end);
        dataMp.put("Page", page);
        dataMp.put("PerPage", perPage);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);

    }

    public BaseModel getOrderListOpen(RequestOrderListOpenParams params) throws NullPointerException {
        validateStartEndPagePerPage(params.getStart(),params.getEnd(),params.getPage(),params.getPerPage());
        Crypto.validateAddress(params.getAddress());
        if (params.getProduct()!=""&&!StrUtils.isProduct(params.getProduct())) throw new InvalidFormatException("invalid product");
        if (params.getSide()!="" && !StrUtils.isProductSide(params.getSide())) throw new InvalidFormatException("invalid side");

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
        return queryJO2BM(jo);
    }

    public BaseModel getOrderListClosed(RequestOrderListClosedParams params) throws NullPointerException {
        validateStartEndPagePerPage(params.getStart(),params.getEnd(),params.getPage(),params.getPerPage());
        Crypto.validateAddress(params.getAddress());
        if (params.getProduct()!="" && !StrUtils.isProduct(params.getProduct())) throw new InvalidFormatException("invalid product");
        if (params.getSide()!="" && !StrUtils.isProductSide(params.getSide())) throw new InvalidFormatException("invalid side");
        if (params.getHideNoFill()!=""&&params.getHideNoFill()!="0"&&params.getHideNoFill()!="1") throw new InvalidFormatException("invalid param hideNoFill");


        String path = "custom/backend/orders/closed";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", params.getProduct());
        dataMp.put("Address", params.getAddress());
        dataMp.put("Side", params.getSide());
        dataMp.put("Page", params.getPage());
        dataMp.put("PerPage", params.getPerPage());
        dataMp.put("Start", params.getStart());
        dataMp.put("End", params.getEnd());
        if (params.getHideNoFill().equals("true")) {
            dataMp.put("HideNoFill", true);
        } else {
            dataMp.put("HideNoFill", false);
        }
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel getDeals(RequestDealsParams params) throws NullPointerException {
        validateStartEndPagePerPage(params.getStart(),params.getEnd(),params.getPage(),params.getPerPage());
        Crypto.validateAddress(params.getAddress());
        if (params.getProduct()!=""&&!StrUtils.isProduct(params.getProduct())) throw new InvalidFormatException("invalid product");
        if (params.getSide()!="" && !StrUtils.isProductSide(params.getSide())) throw new InvalidFormatException("invalid side");

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
        return queryJO2BM(jo);

    }

    public BaseModel getTransactions(RequestTransactionsParams params) throws NullPointerException {
        validateStartEndPagePerPage(params.getStart(),params.getEnd(),params.getPage(),params.getPerPage());
        Crypto.validateAddress(params.getAddress());
        if (!params.getType().equals("")&&!params.getType().equals("1")&&!params.getType().equals("2")&&!params.getType().equals("3")) throw new InvalidFormatException("invalid type");

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
        return queryJO2BM(jo);
    }

    // node query

    private BaseModel nodeQueryJO2BM(JSONObject jo) {
        BaseModel bm = new BaseModel();

        if (jo.containsKey("error")) {
            // if the rpc query fails
            JSONObject tmpJO = jo.getJSONObject("error");
            bm.setCode(tmpJO.getIntValue("code"));
            bm.setDetailMsg(tmpJO.getString("data"));
        } else {
            // if the rpc query succeeds
            bm.setCode(0);
            bm.setData(jo.getString("result"));
        }
        return bm;
    }

    public BaseModel queryCurrentBlock() {
        Map<String, Object> mp = new TreeMap<>();
        mp.put("height", null);
        String res = JSONRPCUtils.call(this.backend, "block", mp);
        return nodeQueryJO2BM(JSON.parseObject(res));
    }

    public BaseModel queryBlock(int height) {
        Map<String, Object> mp = new TreeMap<>();
        mp.put("height", String.valueOf(height));
        String res = JSONRPCUtils.call(this.backend, "block", mp);
        return nodeQueryJO2BM(JSON.parseObject(res));
    }

    public BaseModel queryTx(String txHash, boolean prove) {
        Map<String, Object> mp = new TreeMap<>();
        mp.put("hash", Hex.decode(txHash.toUpperCase()));
        mp.put("prove", prove);
        String res = JSONRPCUtils.call(this.backend, "tx", mp);
        return nodeQueryJO2BM(JSON.parseObject(res));
    }

    public BaseModel queryProposals() {
        String path = "custom/gov/proposals";
        Map<String, Object> mp = new TreeMap<>();
        mp.put("Voter", "");
        mp.put("Depositor", "");
        mp.put("ProposalStatus", "");
        mp.put("Limit", "0");
        byte[] data = JSON.toJSONString(mp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel queryProposalByID(int proposalID) throws Exception {
        if (proposalID < 1) throw new Exception("proposalID can't be less than 1");
        String path = "custom/gov/proposal";
        Map<String, Object> mp = new TreeMap<>();
        mp.put("ProposalID", String.valueOf(proposalID));

        byte[] data = JSON.toJSONString(mp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo);
    }

    public BaseModel queryCurrentValidators() {
        Map<String, Object> mp = new TreeMap<>();
        mp.put("height",  null);
        String res = JSONRPCUtils.call(this.backend, "validators", mp);
        return nodeQueryJO2BM(JSON.parseObject(res));
    }

    public String getTickersV2(String instrumentId) {
        if (!StrUtils.isProduct(instrumentId)) throw new InvalidFormatException("invalid instrumentId");

        String path = "custom/backend/tickersV2";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", instrumentId);
        dataMp.put("Count", "");
        dataMp.put("Sort", true);
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject.toJSONString(queryJO2BM(jo));
    }

    public String getInstrumentsV2() {
        String path = "custom/backend/instrumentsV2";
        JSONObject jo = ABCIQuery(path, null, ConstantIF.RPC_METHOD_QUERY);
        return queryJO2BM(jo).getData();
    }

    public String getOrderListOpenV2(String instrument_id, String after, String before, int limit) throws NullPointerException {
        if (!StrUtils.isProduct(instrument_id)) throw new InvalidFormatException("invalid instrument_id");
        if (after == null) throw new InvalidFormatException("invalid after");
        if (before == null) throw new InvalidFormatException("invalid before");
        if (limit < 0) throw new InvalidFormatException("invalid limit");

        String path = "custom/backend/orderPendingV2";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("Product", instrument_id);
        dataMp.put("After", after);
        dataMp.put("Before", before);
        dataMp.put("Limit", limit+"");
        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject.toJSONString(queryJO2BM(jo));
    }

    public String getOrderV2(String order_id) {
        if (order_id == null || order_id.equals("")) throw new InvalidFormatException("invalid order_id");

        String path = "custom/backend/orderV2";
        Map<String, Object> dataMp = new TreeMap<>();
        dataMp.put("OrderId", order_id);

        byte[] data = JSON.toJSONString(dataMp).getBytes();
        JSONObject jo = ABCIQuery(path, data, ConstantIF.RPC_METHOD_QUERY);
        return JSONObject.toJSONString(queryJO2BM(jo));
    }

    public JSONObject sendPlaceOrderTransactionV2(AccountInfo account, RequestPlaceOrderParams params, String memo) throws IOException {
        checkAccountInfoValue(account);
        checkPlaceOrderRequestParms(params);
        byte[] data = BuildTransaction.generateAminoPlaceOrderTransaction(account, params.getSide(), params.getProduct(), params.getPrice(), params.getQuantity(), memo);
        JSONObject res = sendTransaction(data);
        setOrderMsg(res);
        res.put("client_oid", account.getSequenceNumber());
        String orderId = "";
        if (res.containsKey("events")){
            JSONArray events = res.getJSONArray("events");
            System.out.println(events);
            for (int i=0;i<events.size();i++){
                JSONArray attributes = events.getJSONObject(i).getJSONArray("attributes");
                for (int j=0;j<attributes.size();j++) {
                    JSONObject attribute = attributes.getJSONObject(j);
                    if (attribute.getString("key").equals("orderId")){
                        orderId = attribute.getString("value");
                        break;
                    }
                }
                if (!orderId.equals("")) break;

            }
        }
        res.put("order_id", orderId);

        return res;
    }

    private void setOrderMsg(JSONObject res) {
        if (res.containsKey("code") && res.getIntValue("code") != 0) {
            res.put("error_code", res.getIntValue("code"));
            res.put("error_message", res.get("logs"));
            res.put("result", false);
        }else {
            res.put("error_code", 0);
            res.put("error_message", "");
            res.put("result", true);
        }
    }

    public JSONObject sendCancelOrderTransactionV2(AccountInfo account, String orderId, String memo) throws IOException {
        checkAccountInfoValue(account);
        if (orderId==null||orderId.equals("")) throw new InvalidFormatException("empty orderId");
        if (orderId.length()>30) throw new InvalidFormatException("the length of orderId is too long");
        byte[] data = BuildTransaction.generateAminoCancelOrderTransaction(account, orderId, memo);
        JSONObject res = sendTransaction(data);
        setOrderMsg(res);
        res.put("order_id", orderId);
        res.put("client_oid", account.getSequenceNumber());

        return res;
    }

    public JSONObject sendMultiPlaceOrderTransactionV2(AccountInfo account, List<MultiNewOrderItem> items, String memo) throws IOException {
        checkAccountInfoValue(account);
        for (MultiNewOrderItem item : items){
            RequestPlaceOrderParams params = new RequestPlaceOrderParams(item.getPrice(), item.getProduct(), item.getQuantity(), item.getSide());
            checkPlaceOrderRequestParms(params);
        }

        byte[] data = BuildTransaction.generateAminoMultiPlaceOrderTransaction(account, items, memo);
        JSONObject res = sendTransaction(data);
        setOrderMsg(res);
        res.put("client_oid", account.getSequenceNumber());
        int i;
        ArrayList<String> orderIdMap = new ArrayList<>();
        if (res.containsKey("events")){
            JSONArray events = res.getJSONArray("events");
            System.out.println(events);
            for (i=0;i<events.size();i++){
                JSONArray attributes = events.getJSONObject(i).getJSONArray("attributes");
                for (int j=0;j<attributes.size();j++) {
                    JSONObject attribute = attributes.getJSONObject(j);
                    if (attribute.getString("key").equals("orderId")){
                        String orderId = attribute.getString("value");
                        orderIdMap.add(orderId);
                    }
                }

            }
        }
        res.put("order_id", orderIdMap);

        return res;
    }

    public JSONObject sendMultiCancelOrderTransactionV2(AccountInfo account, List<String> orderIdItems, String memo) throws IOException {
        checkAccountInfoValue(account);
        for (String orderId : orderIdItems) {
            if (orderId==null||orderId.equals("")) throw new InvalidFormatException("empty orderId");
            if (orderId.length()>30) throw new InvalidFormatException("the length of orderId is too long");
        }

        byte[] data = BuildTransaction.generateAminoMultiCancelOrderTransaction(account, orderIdItems, memo);
        JSONObject res = sendTransaction(data);
        setOrderMsg(res);
        //res.put("order_id", orderIdItems);
        res.put("client_oid", account.getSequenceNumber());

        int i;
        ArrayList<String> orderIdMap = new ArrayList<>();
        if (res.containsKey("events")){
            JSONArray events = res.getJSONArray("events");
            //System.out.println(events);
            for (i=0;i<events.size();i++){
                JSONArray attributes = events.getJSONObject(i).getJSONArray("attributes");
                for (int j = 0; j < attributes.size(); j++) {
                    JSONObject attribute = attributes.getJSONObject(j);
                    if (attribute.getString("key").startsWith("ID")) {
                        orderIdMap.add(attribute.getString("key"));
                        // break;
                    }
                }
            }
        }
        res.put("order_id", orderIdMap);

        return res;
    }

}

