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
    private static String privateKey = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";
    // rest服务，端口改为26659

    private static String url = "http://127.0.0.1:26659";
    private static String rpcUrl = "http://127.0.0.1:26657";
    private static String address = "okchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptdqket5k";
    private static String mnemonic = "total lottery arena when pudding best candy until army spoil drill pool";

    @Test
    public void createAddressInfo() {
        // 根据url生成cli
        OKChainClient okc = generateClient();
        // 先生成私钥，后由私钥生成公钥，由公钥生成地址
        AddressInfo addressInfo = okc.createAddressInfo();
        //System.out.println(addressInfo);
        Assert.assertNotNull(addressInfo);
        Assert.assertNotNull(addressInfo.getUserAddress());
        Assert.assertNotNull(addressInfo.getPrivateKey());
        Assert.assertNotNull(addressInfo.getPublicKey());
    }

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
        //System.out.println(mnemonic);
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
            //System.out.println(filename);
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
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
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
        //Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));
    }
    @Test
    public void sendSendTransactions() throws IOException {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        List<String> tos = new ArrayList<>();
        String memo ="";
        // 创建第一笔交易
        String to1 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        tos.add(to1);
        List<Token> amountList1 = new ArrayList<>();
        Token amount1 = new Token();
        amount1.setDenom("okb");
        amount1.setAmount("10.00000000");
        amountList1.add(amount1);

        // 创建第二笔交易
        List<Token> amountList2 = new ArrayList<>();
        String to2 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        tos.add(to2);
        Token amount2 = new Token();
        amount2.setDenom("okb");
        amount2.setAmount("2.00000000");
        amountList2.add(amount2);

        List<List<Token>> amountLists = new ArrayList<>();
        amountLists.add(amountList1);
        amountLists.add(amountList2);

        JSONObject resJson=okc.sendSendTransactions(account,tos,amountLists,memo);
        // resJson是主网收到转账后的答复json对象
        System.out.println(resJson.toString());
        // 判断：resJson中第一级key——"logs"中，第一个元素中(第一个元素为一个新json对象)，key为"success的对应的值是否是true
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));

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
        String url = this.url;
        OKChainClient okc = OKChainClientImpl.getOKChainClient(url);
        //OKChainClient okc = OKChainRPCClientImpl.getOKChainClient(rpcUrl);
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
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getAccountToken() {
        OKChainClient okc = generateClient();
        String address = this.address;
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
        String address = this.address;
        RequestOrderListOpenParams params = new RequestOrderListOpenParams(address);
        BaseModel resJson = okc.getOrderListOpen(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getOrderListClosed() {
        OKChainClient okc = generateClient();
        String address = this.address;
        RequestOrderListClosedParams params = new RequestOrderListClosedParams(address);
        BaseModel resJson = okc.getOrderListClosed(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getDeals() {
        OKChainClient okc = generateClient();
        String address = this.address;
        RequestDealsParams params = new RequestDealsParams(address);
        BaseModel resJson = okc.getDeals(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }

    @Test
    public void getTransactions() {
        OKChainClient okc = generateClient();
        String address = this.address;
        RequestTransactionsParams params = new RequestTransactionsParams(address);
        BaseModel resJson = okc.getTransactions(params);
        String res = JSON.toJSON(resJson).toString();
        System.out.println(res);
        Assert.assertEquals("0", resJson.getCode());
    }
}
