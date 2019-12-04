package com.okchain.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.OKChainClient;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.common.HttpUtils;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class OKChainRPCClientImplTest {
    private static String privateKey = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";
    private static String privateKey2 = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011885";

    private static String mnemo = "total lottery arena when pudding best candy until army spoil drill pool";
    private static String addr = "okchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptdqket5k";
    // rpc
    //private static String url_rpc = "http://localhost:26657";
//    private static String url_rpc = "https://okexbeta.bafang.com/okchain/v1/rpc";
    private static String url_rpc = "http://192.168.13.125:20157";
    private static String queryAddr="okchain1a3xgd3ymuh282fwwawkk9jceml8pex5q0llrhn";
    private static String queryAddr1="okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";

    @Test
    public void testCreateAccount() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo accountInfo = okc.createAccount();
        Assert.assertNotNull(accountInfo);
        System.out.println(accountInfo);
    }

    @Test
    public void testGetAccountInfo() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo accountInfo;
        try{
            accountInfo = okc.getAccountInfo(this.privateKey+"1");
            Assert.assertFalse(true);
        }catch (Exception e){

        }
        accountInfo = okc.getAccountInfo(this.privateKey);
        Assert.assertNotNull(accountInfo.getPrivateKey());
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
        System.out.println(accountInfo);
    }

    @Test
    public void testGetAccountInfoFromMnemonic() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo accountInfo = okc.getAccountInfoFromMnemonic(this.mnemo);
        Assert.assertNotNull(accountInfo.getPrivateKey());
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
        System.out.println(accountInfo);
    }

    @Test
    public void testGenerateMnemonic() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String Mnemonic = client.generateMnemonic();
        Assert.assertNotNull(Mnemonic);
        System.out.println(Mnemonic);
    }

    @Test
    public void getPrivateKeyFromKeyStore() {
        OKChainClient okc = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String password = "1234567";
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

    // transact
    // get readable order-ID from the response
    public String GetOrderID(JSONObject jo) {
        return jo.getString("order_id");
    }

    @Test
    public void testSendSendTransaction() throws NullPointerException, IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo account = client.getAccountInfo(this.privateKey);
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "send memo";
        List<Token> amountList = new ArrayList<>();
        Token amount = new Token("1.00000000", "okb");
        amountList.add(amount);
        JSONObject ret = client.sendSendTransaction(account, to, amountList, memo);
        System.out.println(ret);
    }

    @Test
    public void testSendPlaceOrderTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo account = client.getAccountInfo(this.privateKey);
        String side = "SELL";
        String product = "btc-c9f_okb";
        String price = "1.10000000";
        String quantity = "1.22000000";
        String memo = "new order memo";
        RequestPlaceOrderParams param = new RequestPlaceOrderParams(price, product, quantity, side);
        JSONObject ret = client.sendPlaceOrderTransaction(account, param, memo);
        System.out.println(ret);

        String orderID = GetOrderID(ret);
        System.out.println("orderID:" + orderID);
    }

    @Test
    public void testSendCancelOrderTransaction() throws IOException {
        BuildTransaction.setMode("sync");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        AccountInfo account = client.getAccountInfo(this.privateKey);
        // u can get order-ID by placing a new order
        String orderId = "ID0000045067-1";
        String memo = "cancel order memo";
        JSONObject ret = client.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(ret);
    }

    // query

    @Test
    public void testGetAccountALLTokens() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getAccountALLTokens(this.queryAddr1, "all");
        System.out.println(bm);
        Assert.assertNotNull(bm.getData());
    }

    @Test
    public void testGetAccountToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getAccountToken(this.addr, "okb");
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
        BaseModel bm = client.getToken("okb");
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
        BaseModel bm = client.getDepthBook("btc-c9f_okb");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetCandles() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.getCandles("60", "eos-1e7_okb", "100");
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
        String hideNoFill = "0";
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

    //node query

    @Test
    public void testQueryCurrentBlock() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.queryCurrentBlock();
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testQueryBlock() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.queryBlock(1024);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testQueryTx() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        String txHash = "F6D87D7074E10429470B684842FBB88AE3EC4E2D950F198549A2B2AE8814926C";
        BaseModel bm = client.queryTx(txHash, true);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testQueryProposals() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.queryProposals();
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testQueryProposalByID() throws Exception {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        int proposalID = 1;
        BaseModel bm = client.queryProposalByID(proposalID);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testQueryCurrentValidators() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm = client.queryCurrentValidators();
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

  @Test
  public void getTickersV2() {
      OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
      String bm = client.getTickersV2("btc-c9f_okb");
      System.out.println(bm);

  }

  @Test
  public void getInstrumentsV2() {
      OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
      String bm = client.getInstrumentsV2();
      System.out.println(bm);
  }

  @Test
  public void getOrderListOpenV2() {
      OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
      String bm = client.getOrderListOpenV2("btc-c9f_okb", "", "", 100);
      System.out.println(bm);
  }

  @Test
  public void getOrderV2() {
      OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
      String bm = client.getOrderV2("ID0000012498-1");
      System.out.println(bm);
  }
}