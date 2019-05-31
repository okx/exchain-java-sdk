package io.okchain.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.okchain.client.impl.OKChainClientImpl;
import io.okchain.types.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    @Test
    public void testSendTransferTransaction() {
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

    }

    @Test
    public void testSendOrderTransaction() {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String side = "BUY";
        String product = "xxb_okb";
        String price = "0.10000000";
        String quantity = "1.00000000";
        String memo = "";
        RequestPlaceOrderParams parms = new RequestPlaceOrderParams(price, product, quantity, side);

        JSONObject resJson = okc.sendPlaceOrderTransaction(account, parms, memo);
        System.out.println(resJson.toString());


        String orderId = (String) resJson.getJSONArray("tags").getJSONObject(1).get("value");
        account.setSequenceNumber(Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
        JSONObject resJson2 = okc.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(resJson2.toString());
    }

    @Test
    public void testCancelOrderTransaction() {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String orderId = "ID0000029813-1";
        String memo = "";

        JSONObject resJson = okc.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(resJson.toString());
    }


    private OKChainClient generateClient() {
        String url = "http://192.168.71.35:1317";
        OKChainClient okc = OKChainClientImpl.getOKChainClient(url);
        return okc;
    }

    private AccountInfo generateAccountInfo(OKChainClient okc) {
        String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
        return okc.getAccountInfo(privateKey);
    }

    @Test
    public void getAccountALLTokens() {
        OKChainClient okc = generateClient();
        String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        BaseModel resJson = okc.getAccountALLTokens(address);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getAccountToken() {
        OKChainClient okc = generateClient();
        String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        String symbol = "okb";
        BaseModel resJson = okc.getAccountToken(address, symbol);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getTokens() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getTokens();
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getToken() {
        OKChainClient okc = generateClient();
        String symbol = "okb";
        BaseModel resJson = okc.getToken(symbol);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getProducts() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getProducts();
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getDepthBook() {
        OKChainClient okc = generateClient();
        String product = "xxb_okb";
        BaseModel resJson = okc.getDepthBook(product);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getCandles() {
        OKChainClient okc = generateClient();
        String granularity = "60";
        String instrumentId = "xxb_okb";
        String size = "10";
        BaseModel resJson = okc.getCandles(granularity, instrumentId, size);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getTickers() {
        OKChainClient okc = generateClient();
        String count = "10";
        BaseModel resJson = okc.getTickers(count);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getOrderListOpen() {
        OKChainClient okc = generateClient();
        String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        RequestOrderListOpenParams params = new RequestOrderListOpenParams(address);
        BaseModel resJson = okc.getOrderListOpen(params);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getOrderListClosed() {
        OKChainClient okc = generateClient();
        String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        RequestOrderListClosedParams params = new RequestOrderListClosedParams(address);
        BaseModel resJson = okc.getOrderListClosed(params);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getDeals() {
        OKChainClient okc = generateClient();
        String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        RequestDealsParams params = new RequestDealsParams(address);
        BaseModel resJson = okc.getDeals(params);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }

    @Test
    public void getTransactions() {
        OKChainClient okc = generateClient();
        String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        RequestTransactionsParams params = new RequestTransactionsParams(address);
        BaseModel resJson = okc.getTransactions(params);
        String res = JSON.toJSONString(resJson);
        System.out.println(res);
    }
}
