package com.okchain.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class OKChainRPCClientImplTest {
    private static String privateKey = "de0e9d9e7bac1366f7d8719a450dab03c9b704172ba43e0a25a7be1d51c69a87";
    private static String mnemo = "sustain hole urban away boy core lazy brick wait drive tiger tell";
    private static String addr = "okchain1mm43akh88a3qendlmlzjldf8lkeynq68r8l6ts";
    // rpc
    private static String url_rpc = "http://localhost:26657";


    @Test
    public void getAccountInfo() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo accountInfo = okc.getAccountInfo(this.privateKey);
        System.out.println(JSON.toJSONString(accountInfo));
        Assert.assertNotNull(accountInfo);
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
    }

    @Test
    public void getAddressInfo() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AddressInfo addrInfo = okc.getAddressInfo(this.privateKey);
        System.out.println(addrInfo.getUserAddress());
        Assert.assertNotNull(addrInfo);
    }

    @Test
    public void sendRawBytes() {
        String data = "b401f0625dee0a42c9ee213f0a148c88be1b29942b78c801b43a77dd1050f7ee316512145ab0c4b287f6aa0ab9bf27812351aa11cbfcb98e1a100a036f6b621209313030303030303030126a0a26eb5ae9872102f0fbeb512118816cc20854d6dc45f8e3094d580dfa8252573f0329278fc7a3c41240d7a28eda2aac0d0d9cddd5f7972c5b23a3d9bbe87d8fde95b68abe7099d7ae4064813d6960ea6b6f623244a08181736eb71009191acc8ab6772752489790817a";
        byte[] transactionData = Hex.decode(data);
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        JSONObject res = client.sendTransaction(transactionData);
        System.out.println(res);
    }

    @Test
    public void sendTransaction() {
        String data = "tAHwYl3uCkI8Ht70ChSxLq0NxypP2pN1DUqKCoXeJPNCshImChT0uZ4HU4lve6xWlR9H8BEW8ZwDABIOCgNva2ISBzIyNTAwMDASagom61rphyED1C8GqfBLEgskTUfp2VjnZfYGiojlJuIBMJvj1XcLWd4SQLhEzVhqEyQE4JwkZojT7zMcHqYdtdqK6roZKvEN8gkLbKPqCTTn+bsmOcb4qr1BNe0/rVyxu14vQ6QTvT2UpQw=";
        byte[] transactionData = Base64.getDecoder().decode(data);
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        JSONObject res = client.sendTransaction(transactionData);
        System.out.println(res);
    }

    @Test
    public void testSendSendTransaction() throws NullPointerException, IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo account = client.getAccountInfo(this.privateKey);
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "I love okb";
        List<Token> amountList = new ArrayList<>();
        Token amount = new Token("10.00000000", "okb");
        amountList.add(amount);
        JSONObject ret = client.sendSendTransaction(account, to, amountList, memo);
        System.out.println(ret);
    }

    @Test
    public void testSendPlaceOrderTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo account = client.getAccountInfo(this.privateKey);
        String side = "BUY";
        String product = "xxb_okb";
        String price = "1.10000000";
        String quantity = "1.22000000";
        String memo = "I love okb";
        RequestPlaceOrderParams param = new RequestPlaceOrderParams(price, product, quantity, side);
        JSONObject ret = client.sendPlaceOrderTransaction(account, param, memo);
        System.out.println(ret);

        String orderID = GetOrderID(ret);
        System.out.println("orderID:" + orderID);
    }

    // get readable order-ID from the response
    public String GetOrderID(JSONObject jo) {
        String encodedId = jo.getJSONObject("deliver_tx").getJSONArray("tags").getJSONObject(1).getString("value");
        return new String(Base64.getDecoder().decode(encodedId));
    }

    @Test
    public void testSendCancelOrderTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo account = client.getAccountInfo(this.privateKey);
        // u can get order-ID by placing a new order
        String orderId = "ID0000001845-1";
        String memo = "I love okb";
        JSONObject ret = client.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(ret);

    }

    @Test
    public void testSendMultiSendTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo account = client.getAccountInfo(this.privateKey);

        List<TransferUnit> transferUnits = new ArrayList<>();
        // create the 1st tx
        String to1 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";
        List<Token> amounts1 = new ArrayList<>();
        amounts1.add(new Token("1.90000000", "okb"));
        transferUnits.add(new TransferUnit(amounts1, to1));
        // create the 2nd tx
        String to2 = "okchain1qsc6ckz6uvrqdxg36p6rrwp25kyg4dtl8gdr92";
        List<Token> amounts2 = new ArrayList<>();
        amounts2.add(new Token("10.10000000", "okb"));
        transferUnits.add(new TransferUnit(amounts2, to2));
        JSONObject ret = client.sendMultiSendTransaction(account, transferUnits, memo);
        System.out.println(ret);
    }

    @Test
    public void testCreateAddressInfo() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);

        AddressInfo addrInfo = client.createAddressInfo();
        System.out.println(addrInfo.getPrivateKey());
        System.out.println(addrInfo.getPublicKey());
        System.out.println(addrInfo.getUserAddress());
        Assert.assertNotNull(addrInfo);
    }

    @Test
    public void testGenerateMnemonic() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String Mnemonic = client.generateMnemonic();
        System.out.println(Mnemonic);
        Assert.assertNotNull(Mnemonic);
    }

    @Test
    public void testGetPrivateKeyFromMnemonic() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String privKey = client.getPrivateKeyFromMnemonic(this.mnemo);
        System.out.println(privKey);
        Assert.assertNotNull(privKey);
        AddressInfo addrInfo = client.getAddressInfo(privKey);
        System.out.println(addrInfo.getPrivateKey());
        System.out.println(addrInfo.getPublicKey());
        System.out.println(addrInfo.getUserAddress());
        Assert.assertNotNull(addrInfo);
    }

    @Test
    public void testGetAccountFromNode() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        JSONObject jo = client.getAccountFromNode(this.addr);
        System.out.println(jo);
        Assert.assertFalse(jo.containsKey("code"));
    }

    @Test
    public void testGetAccountALLTokens() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getAccountALLTokens(this.addr, "all");
        System.out.println(bm);
        Assert.assertNotNull(bm.getData());
    }

    @Test
    public void testGetAccountToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getAccountToken(this.addr, "gyc-3b3");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetTokens() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getTokens();
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getToken("gyc-3b3");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetProducts() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getProducts();
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetDepthBook() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getDepthBook("xxb_okb");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetCandles() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getCandles("60", "xxb_okb", "100");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetTickers() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getTickers("10");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetMatches() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        // get the system time right now and convert it to String
        String nowTimeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        BaseModel bm = client.getMatches("xxb_okb", "0", nowTimeStamp, "0", "10");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetOrderListOpen() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String product = "xxb_okb";
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestOrderListOpenParams olop = new RequestOrderListOpenParams(product, this.addr, start, end, side, page, perPage);
        BaseModel bm = client.getOrderListOpen(olop);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetOrderListClosed() {
        // cancel a order first by okchaincli
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String product = "xxb_okb";
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        // if input hideNofill is not "true", we always treat it as "false"
        String hideNoFill = "false";
        RequestOrderListClosedParams olcp = new RequestOrderListClosedParams(product, this.addr, start, end, side, page, perPage, hideNoFill);
        BaseModel bm = client.getOrderListClosed(olcp);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetDeals() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String product = "xxb_okb";
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestDealsParams rdp = new RequestDealsParams(product, this.addr, start, end, side, page, perPage);
        BaseModel bm = client.getDeals(rdp);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetTransactions() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String type = "1";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestTransactionsParams rtp = new RequestTransactionsParams(this.addr, type, start, end, page, perPage);
        BaseModel bm = client.getTransactions(rtp);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

}