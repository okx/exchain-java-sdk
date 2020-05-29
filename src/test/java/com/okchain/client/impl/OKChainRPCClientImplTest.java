package com.okchain.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.okchain.client.OKChainClient;
import com.okchain.common.StrUtils;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OKChainRPCClientImplTest {
    private static String PRIVATEKEY = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";
    private static String PRIVATEKEY2 = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011885";

    private static String MNEMO = "total lottery arena when pudding best candy until army spoil drill pool";
    private static String ADDR = "okchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptdqket5k";

    private static String URL_RPC = "http://localhost:26657";

    private static String QUERYADDR = "okchain1a3xgd3ymuh282fwwawkk9jceml8pex5q0llrhn";
    private static String QUERYADDR1 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";

    private static String TEST_COIN_NAME = "xxb-d35";
    private static String BASE_COIN_NAME = "tokt";
    private static String TEST_PRODUCT = TEST_COIN_NAME + "_" + BASE_COIN_NAME;

    @Test
    public void testCreateAccount() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient("");
        AccountInfo accountInfo = okc.createAccount();
        Assert.assertNotNull(accountInfo);
    }

    @Test
    public void testGetAccountInfo() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo accountInfo;
        try {
            accountInfo = okc.getAccountInfo(PRIVATEKEY + "1");
            Assert.assertFalse(true);
        } catch (Exception e) {

        }
        accountInfo = okc.getAccountInfo(PRIVATEKEY);
        Assert.assertNotNull(accountInfo.getPrivateKey());
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
    }

    @Test
    public void testGetAccountInfoFromMnemonic() {
        OKChainRPCClientImpl okc = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo accountInfo = okc.getAccountInfoFromMnemonic(MNEMO);
        Assert.assertNotNull(accountInfo.getPrivateKey());
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
    }

    @Test
    public void testGenerateMnemonic() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient("");
        String mnemonic = client.generateMnemonic();
        Assert.assertNotNull(mnemonic);
    }

    @Test
    public void getPrivateKeyFromKeyStore() {
        OKChainClient okc = OKChainRPCClientImpl.getOKChainClient("");
        String password = "1234567";
        String filename = "";
        try {
            filename = okc.generateKeyStore(PRIVATEKEY, password);
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
            String priv = okc.getPrivateKeyFromKeyStore(filename, password);
            Assert.assertEquals(PRIVATEKEY, priv);
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
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";
        List<Token> amountList = new ArrayList<>();
        Token amount = new Token("1.00000000", BASE_COIN_NAME);
        amountList.add(amount);
        JSONObject ret = client.sendSendTransaction(account, to, amountList, memo);
        Assert.assertNotNull(ret);
        Assert.assertEquals(true, ret.getJSONArray("logs").getJSONObject(0).get("success"));

    }

    @Test
    public void testSendPlaceOrderAndCancelOrderTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
        String side = "BUY";
        String product = TEST_PRODUCT;
        String price = "1.10000000";
        String quantity = "1.22000000";
        String memo = "new order memo";
        RequestPlaceOrderParams param = new RequestPlaceOrderParams(price, product, quantity, side);
        JSONObject ret = client.sendPlaceOrderTransaction(account, param, memo);
        Assert.assertNotNull(ret);

        Assert.assertEquals(true, ret.getJSONArray("logs").getJSONObject(0).get("success"));

        String orderId = StrUtils.getOrderIdFromResult(ret);
        account.setSequenceNumber(
                Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
        JSONObject resJson2 = client.sendCancelOrderTransaction(account, orderId, memo);
        Assert.assertEquals(true, resJson2.getJSONArray("logs").getJSONObject(0).get("success"));
    }


    @Test
    public void testSendMultiSendTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
        List<TransferUnit> transferUnits = new ArrayList<>();
        // create the 1st tx
        String to1 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";
        List<Token> amounts1 = new ArrayList<>();
        amounts1.add(new Token("1.00000000", BASE_COIN_NAME));
        amounts1.add(new Token("5.50000000", TEST_COIN_NAME));
        transferUnits.add(new TransferUnit(amounts1, to1));
        // create the 2nd tx
        String to2 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        List<Token> amounts2 = new ArrayList<>();
        amounts2.add(new Token("10.00000000", BASE_COIN_NAME));
        amounts2.add(new Token("50.00000000", TEST_COIN_NAME));
        transferUnits.add(new TransferUnit(amounts2, to2));
        JSONObject ret = client.sendMultiSendTransaction(account, transferUnits, memo);
        Assert.assertNotNull(ret);
        Assert.assertEquals(true, ret.getJSONArray("logs").getJSONObject(0).get("success"));

    }


    // query
    @Test
    public void testGetAccountALLTokens() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getAccountALLTokens(QUERYADDR1, "all");
        Assert.assertNotNull(bm.getData());
    }

    @Test
    public void testGetAccountToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getAccountToken(ADDR, BASE_COIN_NAME);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetTokens() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getTokens();
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getToken(BASE_COIN_NAME);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetProducts() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getProducts();
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetDepthBook() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getDepthBook(TEST_PRODUCT);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetCandles() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getCandles("60", "eos-1e7_okt", "100");
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetTickers() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.getTickers("10");
        System.out.println(bm.toString());
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetMatches() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        // get the system time right now and convert it to String
        String nowTimeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        BaseModel bm = client.getMatches(TEST_PRODUCT, "0", nowTimeStamp, "0", "10");
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetOrderListOpen() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String product = TEST_PRODUCT;
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestOrderListOpenParams olop = new RequestOrderListOpenParams(product, ADDR, start, end, side, page, perPage);
        BaseModel bm = client.getOrderListOpen(olop);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetOrderListClosed() {
        // cancel a order first by okchaincli
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String product = TEST_PRODUCT;
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        // if input hideNofill is not "true", we always treat it as "false"
        String hideNoFill = "0";
        RequestOrderListClosedParams olcp = new RequestOrderListClosedParams(product, ADDR, start, end, side, page, perPage, hideNoFill);
        BaseModel bm = client.getOrderListClosed(olcp);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetDeals() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String product = TEST_PRODUCT;
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestDealsParams rdp = new RequestDealsParams(product, ADDR, start, end, side, page, perPage);
        BaseModel bm = client.getDeals(rdp);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetTransactions() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String type = "1";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestTransactionsParams rtp = new RequestTransactionsParams(ADDR, type, start, end, page, perPage);
        BaseModel bm = client.getTransactions(rtp);
        Assert.assertEquals(bm.getCode(), 0);
    }

    //node query

    @Test
    public void testQueryCurrentBlock() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.queryCurrentBlock();
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testQueryBlock() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.queryBlock(1024);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testQueryTx() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String txHash = "F6D87D7074E10429470B684842FBB88AE3EC4E2D950F198549A2B2AE8814926C";
        BaseModel bm = client.queryTx(txHash, true);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testQueryProposals() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.queryProposals();
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testQueryProposalByID() throws Exception {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        int proposalID = 1;
        BaseModel bm = client.queryProposalByID(proposalID);
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testQueryCurrentValidators() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        BaseModel bm = client.queryCurrentValidators();
        Assert.assertEquals(bm.getCode(), 0);
    }

    @Test
    public void testGetTickersV2() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String bm = client.getTickersV2("btc-c9f_okt");
        Assert.assertNotNull(bm);
    }

    @Test
    public void testGetInstrumentsV2() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String bm = client.getInstrumentsV2();
        Assert.assertNotNull(bm);
    }

    @Test
    public void testGetOrderListOpenV2() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String bm = client.getOrderListOpenV2(TEST_PRODUCT, "", "", 100);
        Assert.assertNotNull(bm);
    }

    @Test
    public void testGetOrderV2() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        String bm = client.getOrderV2("ID0000000006-1");
        Assert.assertNotNull(bm);
    }

    @Test
    public void testSendPlaceOrderTransactionV2() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
        String side = "BUY";
        String product = TEST_PRODUCT;
        String price = "1.10000000";
        String quantity = "1.22000000";
        String memo = "new order memo";
        RequestPlaceOrderParams param = new RequestPlaceOrderParams(price, product, quantity, side);
        JSONObject ret = client.sendPlaceOrderTransactionV2(account, param, memo);
        Assert.assertNotNull(ret);

        String orderID = GetOrderID(ret);
        Assert.assertNotNull(orderID);
    }

    @Test
    public void testSendCancelOrderTransactionV2() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
        // u can get order-ID by placing a new order
        String orderId = "ID0000001777-1";
        String memo = "cancel order memo";
        JSONObject ret = client.sendCancelOrderTransactionV2(account, orderId, memo);
        Assert.assertNotNull(ret);
    }

    private MultiNewOrderItem getMultiNewOrderItemWithBUY() {
        String side = "BUY";
        String product = TEST_PRODUCT;
        String price = "1.10000000";
        String quantity = "1.22000000";
        return new MultiNewOrderItem(price, product, quantity, side);
    }

    private MultiNewOrderItem getMultiNewOrderItemWithSELL() {
        String side = "SELL";
        String product = TEST_PRODUCT;
        String price = "1.10000000";
        String quantity = "1.22000000";
        return new MultiNewOrderItem(price, product, quantity, side);
    }

//    @Test
//    public void testMarket() throws IOException {
//        BuildTransaction.setMode("async");
//        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
//        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
//        String memo = "new order memo";
//        for (;;) {
//            List<MultiNewOrderItem> items = new ArrayList<>();
//            for (int i = 0; i < 5; i++) {
//                MultiNewOrderItem itemBuy = getMultiNewOrderItemWithBUY();
//                MultiNewOrderItem itemSell = getMultiNewOrderItemWithSELL();
//                items.add(itemBuy);
//                items.add(itemSell);
//            }
//
//            JSONObject ret = client.sendMultiPlaceOrderTransactionV2(account, items, memo);
//            Assert.assertNotNull(ret);
//            System.out.println(ret);
//            account.setSequenceNumber(
//                    Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

    @Test
    public void testSendMultiPlaceOrderTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
        String side = "BUY";
        String product = TEST_PRODUCT;
        String price = "1.10000000";
        String quantity = "1.22000000";
        String memo = "new order memo";
        MultiNewOrderItem param = new MultiNewOrderItem(price, product, quantity, side);
        MultiNewOrderItem param2 = new MultiNewOrderItem(price, product, quantity, side);

        List<MultiNewOrderItem> items = new ArrayList<>();
        items.add(param);
        items.add(param2);
        JSONObject ret = client.sendMultiPlaceOrderTransactionV2(account, items, memo);
        Assert.assertNotNull(ret);
        System.out.println(ret);
        String orderID = GetOrderID(ret);
        Assert.assertNotNull(orderID);
    }

    @Test
    public void testSendMultiCancelOrderTransaction() throws IOException {
        BuildTransaction.setMode("block");
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(URL_RPC);
        AccountInfo account = client.getAccountInfo(PRIVATEKEY);
        // u can get order-ID by placing a new order
        String orderId = "ID1-0000000039-2";
        String orderId2 = "ID1-0000000576-1";
        String memo = "cancel order memo";
        List<String> orderIditems = new ArrayList<>();
        orderIditems.add(orderId);
        orderIditems.add(orderId2);
        JSONObject ret = client.sendMultiCancelOrderTransactionV2(account, orderIditems, memo);
        System.out.println(ret);
        Assert.assertNotNull(ret);
    }

}
