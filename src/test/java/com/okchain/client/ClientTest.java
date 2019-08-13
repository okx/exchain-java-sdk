package com.okchain.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.impl.OKChainClientImpl;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    private static String privateKey = "de0e9d9e7bac1366f7d8719a450dab03c9b704172ba43e0a25a7be1d51c69a87";
    private static String mnemo = "sustain hole urban away boy core lazy brick wait drive tiger tell";
    private static String addr = "okchain1mm43akh88a3qendlmlzjldf8lkeynq68r8l6ts";
    private static String url_rest = "http://127.0.0.1:26659";

    @Test
    public void createAddressInfo() {
        OKChainClient okc = generateClient();
        AddressInfo addrInfo = okc.createAddressInfo();
        System.out.println(addrInfo.getUserAddress());
        System.out.println(addrInfo.getPrivateKey());
        System.out.println(addrInfo.getPublicKey());
        Assert.assertNotNull(addrInfo);
        Assert.assertNotNull(addrInfo.getUserAddress());
        Assert.assertNotNull(addrInfo.getPrivateKey());
        Assert.assertNotNull(addrInfo.getPublicKey());
    }

    @Test
    public void getAddressInfo() {
        OKChainClient okc = generateClient();
        AddressInfo addrInfo = okc.getAddressInfo(this.privateKey);
        System.out.println(addrInfo.getUserAddress());
        Assert.assertEquals(this.addr, addrInfo.getUserAddress());
        Assert.assertNotNull(addrInfo.getPrivateKey());
        Assert.assertNotNull(addrInfo.getPublicKey());
    }

    @Test
    public void getAccountInfo() {
        OKChainClient okc = generateClient();
        AccountInfo accountInfo = okc.getAccountInfo(this.privateKey);
        System.out.println(accountInfo.getUserAddress());
        System.out.println(accountInfo.getAccountNumber());
        System.out.println(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo);
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
    }

    @Test
    public void getPrivateKeyFromMnemonic() {
        OKChainClient okc = generateClient();
        String privateKey = okc.getPrivateKeyFromMnemonic(this.mnemo);
        System.out.println(privateKey);
        Assert.assertEquals(this.privateKey, privateKey);
    }

    @Test
    public void generateMnemonic() {
        OKChainClient okc = generateClient();
        String mnemonic = okc.generateMnemonic();
        System.out.println(mnemonic);
        String[] words = mnemonic.split(" ");
        Assert.assertEquals(12, words.length);
    }

    @Test
    public void getPrivateKeyFromKeyStore() {
        OKChainClient okc = generateClient();
        String password = "12345678";
        String filename = "";
        try {
            filename = okc.generateKeyStore(this.privateKey, password);
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
        file.delete();
    }

    @Test
    public void sendSendTransaction() throws IOException {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);
        JSONObject resJson = okc.sendSendTransaction(account, to, amountList, memo);
        System.out.println(resJson.toString());
        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
    }

    @Test
    public void testSendCancelOrderTransaction() throws IOException {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String side = "BUY";
        String product = "xxb_okb";
        String price = "0.10000000";
        String quantity = "1.00000000";
        String memo = "";
        BuildTransaction.setMode("block");
        RequestPlaceOrderParams parms = new RequestPlaceOrderParams(price, product, quantity, side);

        JSONObject resJson = okc.sendPlaceOrderTransaction(account, parms, memo);
        System.out.println(resJson.toString());
        //Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));


        String orderId = (String) resJson.getJSONArray("tags").getJSONObject(1).get("value");
        account.setSequenceNumber(Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
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

    @Test
    public void sendMultiSendTransaction() throws IOException {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String to2 = "okchain1eutyuqqase3eyvwe92caw8dcx5ly8s54lnazy5";
        String memo = "";

        String amount = "1.00000000";
        String denom = "okb";

        List<TransferUnit> transfers = new ArrayList<>();
        transfers.add(generateTrasferUnit(to, denom, amount));
        transfers.add(generateTrasferUnit(to2, denom, amount));

        JSONObject resJson = okc.sendMultiSendTransaction(account, transfers, memo);
        System.out.println(resJson.toString());
        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
        //Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));
    }

    private OKChainClient generateClient() {
        String url = this.url_rest;
        OKChainClient okc = OKChainClientImpl.getOKChainClient(url);
        return okc;
    }

    private AccountInfo generateAccountInfo(OKChainClient okc) {
        String privateKey = this.privateKey;
        return okc.getAccountInfo(privateKey);
    }

    @Test
    public void getAccountALLTokens() {
        OKChainClient okc = generateClient();
        String address = this.addr;
        String show = "all";
        BaseModel resJson = okc.getAccountALLTokens(address, show);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getAccountToken() {
        OKChainClient okc = generateClient();
        String address = this.addr;
        String symbol = "okb";
        BaseModel resJson = okc.getAccountToken(address, symbol);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getTokens() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getTokens();
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getToken() {
        OKChainClient okc = generateClient();
        String symbol = "okb";
        BaseModel resJson = okc.getToken(symbol);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getProducts() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getProducts();
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getDepthBook() {
        OKChainClient okc = generateClient();
        String product = "xxb_okb";
        BaseModel resJson = okc.getDepthBook(product);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getCandles() {
        OKChainClient okc = generateClient();
        String granularity = "60";
        String instrumentId = "xxb_okb";
        String size = "10";
        BaseModel resJson = okc.getCandles(granularity, instrumentId, size);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getTickers() {
        OKChainClient okc = generateClient();
        String count = "10";
        BaseModel resJson = okc.getTickers(count);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getMatches() {
        OKChainClient okc = generateClient();
        String product = "xxb_okb";
        BaseModel resJson = okc.getMatches(product, "", "", "", "");
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getOrderListOpen() {
        OKChainClient okc = generateClient();
        String address = this.addr;
        RequestOrderListOpenParams params = new RequestOrderListOpenParams(address);
        BaseModel resJson = okc.getOrderListOpen(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getOrderListClosed() {
        OKChainClient okc = generateClient();
        String address = this.addr;
        RequestOrderListClosedParams params = new RequestOrderListClosedParams(address);
        BaseModel resJson = okc.getOrderListClosed(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getDeals() {
        OKChainClient okc = generateClient();
        String address = this.addr;
        RequestDealsParams params = new RequestDealsParams(address);
        BaseModel resJson = okc.getDeals(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getTransactions() {
        OKChainClient okc = generateClient();
        String address = this.addr;
        RequestTransactionsParams params = new RequestTransactionsParams(address);
        BaseModel resJson = okc.getTransactions(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }
}
