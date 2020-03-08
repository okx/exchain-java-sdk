package com.okchain.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.OKChainClient;
import com.okchain.client.impl.OKChainRestClientImpl;
import com.okchain.crypto.Crypto;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import com.okchain.types.staking.CommissionRates;
import com.okchain.types.staking.Description;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OKChainRestClientImplTest {

    // okchain1v853tq96n9ghvyxlvqyxyj97589clccr33yr7a
    private static String privateKey =
            "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";
    // rest服务，端口改为26659

    private static String url = "http://127.0.0.1:26659";
    private static String address = "okchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptdqket5k";
    private static String mnemonic =
            "total lottery arena when pudding best candy until army spoil drill pool";

    @Test
    public void getAddressInfo() {
        OKChainClient okc = generateClient();
        // 通过私钥获得公钥和地址
        AddressInfo addressInfo = okc.getAddressInfo(this.privateKey);
        Assert.assertNotNull(addressInfo);
        Assert.assertEquals(this.address, addressInfo.getUserAddress());
        Assert.assertNotNull(addressInfo.getPrivateKey());
        Assert.assertNotNull(addressInfo.getPublicKey());
    }

    @Test
    public void getAccountInfo() {
        OKChainClient okc = generateClient();
        AccountInfo accountInfo = okc.getAccountInfo(this.privateKey);
        System.out.println(accountInfo);
        Assert.assertNotNull(accountInfo);
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
    }

    @Test
    // 从助记词中获得私钥
    public void getPrivateKeyFromMnemonic() {
        OKChainClient okc = generateClient();
        String privateKey = okc.getPrivateKeyFromMnemonic(this.mnemonic);
        Assert.assertEquals(this.privateKey, privateKey);
    }

    @Test
    public void generateMnemonic() {
        OKChainClient okc = generateClient();
        // 创建助记词
        String mnemonic = okc.generateMnemonic();
        // System.out.println(mnemonic);
        String[] words = mnemonic.split(" ");
        Assert.assertEquals(12, words.length);
    }

    @Test
    public void getPrivateKeyFromKeyStore() {
        OKChainClient okc = generateClient();
        String password = "jilei";
        String filename = "";
        try {
            // 用私钥和密码生成KeyStore文件
            filename = okc.generateKeyStore(this.privateKey, password);
            // System.out.println(filename);
            Assert.assertNotNull(filename);
            Assert.assertNotEquals("", filename);
        } catch (CipherException e) {
            e.printStackTrace();
            Assert.assertNull(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertNull(e.getMessage());
        }
        try {
            String privateKey = okc.getPrivateKeyFromKeyStore(filename, password);
            System.out.println(privateKey);
            Assert.assertEquals(privateKey, this.privateKey);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertNull(e.getMessage());
        } catch (CipherException e) {
            e.printStackTrace();
            Assert.assertNull(e.getMessage());
        }
        File file = new File(filename);
        // System.out.println(filename);
        file.delete();
    }

    @Test
    public void sendSendTransaction() throws IOException {
        OKChainClient okc = generateClient();
        // okc中包含两个东西：url和backend。
        AccountInfo account = generateAccountInfo(okc);
        // generateAccountInfo会从测试类中拿到私钥
        // generateAccountInfo中有函数getAccountInfo，getAccountInfo函数中
        // getAddressInfo通过私钥找到公钥和ok地址
        // account是从主网get到的ok addr上的一些信息

        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okt");
        amount.setAmount("16.00000000");
        amountList.add(amount);
        // account是客户端的账户信息（通过私钥到公钥到OK地址然后向主网查询get该地址下的信息返回形成一个account）
        // to是要转账给的ok地址
        // amountList是要转账的集合
        JSONObject resJson = okc.sendSendTransaction(account, to, amountList, memo);
        // resJson是主网收到转账后的答复json对象
        System.out.println(resJson.toString());
        // 判断：resJson中第一级key——"logs"中，第一个元素中(第一个元素为一个新json对象)，key为"success的对应的值是否是true

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
        // Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));
    }

    @Test
    public void testSendCancelOrderTransaction() throws IOException {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String side = "BUY";
        String product = "xxb_okt";
        String price = "0.10000000";
        String quantity = "1.00000000";
        String memo = "";
        BuildTransaction.setMode("block");
        RequestPlaceOrderParams parms = new RequestPlaceOrderParams(price, product, quantity, side);

        JSONObject resJson = okc.sendPlaceOrderTransaction(account, parms, memo);
        System.out.println(resJson.toString());
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));

        String orderId = getOrderIdFromResult(resJson);
        account.setSequenceNumber(
                Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
        JSONObject resJson2 = okc.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(resJson2.toString());
        Assert.assertEquals(true, resJson2.getJSONArray("logs").getJSONObject(0).get("success"));
    }

    private TransferUnit generateTrasferUnit(String to, String denom, String amount) {
        List<Token> coins = new ArrayList<>();
        Token coin = new Token();
        coin.setDenom(denom);
        coin.setAmount(amount);
        coins.add(coin);
        TransferUnit transferUint = new TransferUnit(coins, to);
        return transferUint;
    }

    private String getOrderIdFromResult(JSONObject result) {
        String orderId = "";
        JSONArray events = result.getJSONArray("events");

        for (Iterator<Object> iterator = events.iterator(); iterator.hasNext(); ) {
            JSONObject event = (JSONObject) iterator.next();
            JSONArray attributes = event.getJSONArray("attributes");
            for (Iterator<Object> attributesIterator = attributes.iterator();
                    attributesIterator.hasNext(); ) {
                JSONObject attribute = (JSONObject) attributesIterator.next();
                //                System.out.println(attribute);
                if (attribute.getString("key").equals("orderId")) {
                    return attribute.getString("value");
                }
            }
        }
        return orderId;
    }

    @Test
    public void sendCreateValidatorTransaction() throws IOException {
        OKChainClient okc = generateClient();
        BuildTransaction.setMode("block");
        AccountInfo account = generateAccountInfo(okc);

        String memo = "";
        Description description = new Description("m1", "1", "1", "1");
        CommissionRates commission = new CommissionRates("0.10000000", "0.50000000", "0.00100000");
        String delegatorAddress = account.getUserAddress();
        String validatorAddress = Crypto.generateValidatorAddressFromPub(account.getPublicKey());
//        String validatorAddress = "okchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frs863s3m";

        System.out.println(validatorAddress);

        String pubKey = "okchainvalconspub1zcjduepqtv2yy90ptjegdm34vfhlq2uw9eu39hjrt98sffj7yghl4s47xv7swuf0dx";
        Token minSelfDelegation = new Token();
        minSelfDelegation.setDenom("okt");
        minSelfDelegation.setAmount("1000.00000000");

        JSONObject resJson = okc.sendCreateValidatorTransaction(account, description,
                commission, minSelfDelegation, delegatorAddress, validatorAddress, pubKey, memo);

        System.out.println(resJson.toString());

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
    }

    @Test
    public void sendEditValidatorTransaction() throws IOException {
        OKChainClient okc = generateClient();
        BuildTransaction.setMode("block");

        AccountInfo account = generateAccountInfo(okc);

        String memo = "";
        Description description = new Description("m1", "1", "1", "1");

        String validatorAddress = Crypto.generateValidatorAddressFromPub(account.getPublicKey());

        System.out.println(validatorAddress);

        String minSelfDelegation = "1100";

        JSONObject resJson = okc.sendEditValidatorTransaction(account, minSelfDelegation, validatorAddress, description, memo);

        System.out.println(resJson.toString());

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
    }

    private OKChainClient generateClient() {
        String url = this.url;
        OKChainClient okc = OKChainRestClientImpl.getOKChainClient(url);
        return okc;
    }

    private AccountInfo generateAccountInfo(OKChainClient okc) {
        String privateKey = this.privateKey;
        return okc.getAccountInfo(privateKey);
    }

    @Test
    public void getAccountALLTokens() {
        OKChainClient okc = generateClient();
        String address = this.address;
        String show = "all";
        BaseModel resJson = okc.getAccountALLTokens(address, show);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getAccountToken() {
        OKChainClient okc = generateClient();
        String address = this.address;
        String symbol = "okt";
        BaseModel resJson = okc.getAccountToken(address, symbol);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTokens() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getTokens();
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getToken() {
        OKChainClient okc = generateClient();
        String symbol = "okt";
        BaseModel resJson = okc.getToken(symbol);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getProducts() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getProducts();
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getDepthBook() {
        OKChainClient okc = generateClient();
        String product = "xxb_okt";
        BaseModel resJson = okc.getDepthBook(product);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getCandles() {
        OKChainClient okc = generateClient();
        String granularity = "60";
        String instrumentId = "xxb_okt";
        String size = "10";
        BaseModel resJson = okc.getCandles(granularity, instrumentId, size);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTickers() {
        OKChainClient okc = generateClient();
        String count = "10";
        BaseModel resJson = okc.getTickers(count);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getOrderListOpen() {
        OKChainClient okc = generateClient();
        String address = this.address;
        RequestOrderListOpenParams params = new RequestOrderListOpenParams(address);
        BaseModel resJson = okc.getOrderListOpen(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getOrderListClosed() {
        OKChainClient okc = generateClient();
        String address = this.address;
        RequestOrderListClosedParams params = new RequestOrderListClosedParams(address);
        BaseModel resJson = okc.getOrderListClosed(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getDeals() {
        OKChainClient okc = generateClient();
        String address = this.address;
        RequestDealsParams params = new RequestDealsParams(address);
        BaseModel resJson = okc.getDeals(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTransactions() {
        OKChainClient okc = generateClient();
        String address = this.address;
        RequestTransactionsParams params = new RequestTransactionsParams(address);
        BaseModel resJson = okc.getTransactions(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals(0, resJson.getCode());
    }
}
