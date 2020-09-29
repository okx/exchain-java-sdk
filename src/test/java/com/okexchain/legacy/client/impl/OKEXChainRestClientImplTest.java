package com.okexchain.legacy.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okexchain.legacy.client.OKEXChainClient;
import com.okexchain.legacy.common.ConstantIF;
import com.okexchain.legacy.common.StrUtils;
import com.okexchain.legacy.crypto.Crypto;
import com.okexchain.legacy.types.*;
import com.okexchain.legacy.types.staking.CommissionRates;
import com.okexchain.legacy.types.staking.Description;
import com.okexchain.legacy.crypto.keystore.CipherException;
import com.okexchain.legacy.transaction.BuildTransaction;
import io.okexchain.types.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OKEXChainRestClientImplTest {

    private static String PRIVATEKEY = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";
    // rest service port is 26659
    private static String URL = "http://127.0.0.1:26659";
    private static String ADDRESS = "okexchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptddw77gw";
    private static String MNEMONIC = "total lottery arena when pudding best candy until army spoil drill pool";

    private static String TEST_COIN_NAME = "xxb-17c";
    private static String BASE_COIN_NAME = ConstantIF.BASE_COIN_NAME;
    private static String TEST_PRODUCT = TEST_COIN_NAME + "_" + BASE_COIN_NAME;

    @Test
    public void getAddressInfoTest() {
        OKEXChainClient okc = generateClient();
        // get publicKey and address with privateKey
        AddressInfo addressInfo = okc.getAddressInfo(PRIVATEKEY);
        Assert.assertNotNull(addressInfo);
        Assert.assertEquals(ADDRESS, addressInfo.getUserAddress());
        Assert.assertNotNull(addressInfo.getPrivateKey());
        Assert.assertNotNull(addressInfo.getPublicKey());
    }

    @Test
    public void getAccountInfoTest() {
        OKEXChainClient okc = generateClient();
        AccountInfo accountInfo = okc.getAccountInfo(PRIVATEKEY);
        System.out.println(accountInfo);
        Assert.assertNotNull(accountInfo);
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
    }

    @Test
    // get privateKey with mnemonic
    public void getPrivateKeyFromMnemonicTest() {
        OKEXChainClient okc = generateClient();
        String privateKey = okc.getPrivateKeyFromMnemonic(MNEMONIC);
        Assert.assertEquals(PRIVATEKEY, privateKey);
    }

    @Test
    public void generateMnemonicTest() {
        OKEXChainClient okc = generateClient();
        // create mnemonic
        String mnemonic = okc.generateMnemonic();
        String[] words = mnemonic.split(" ");
        Assert.assertEquals(12, words.length);
    }

    @Test
    public void getPrivateKeyFromKeyStoreTest() {
        OKEXChainClient okc = generateClient();
        String password = "testPass";
        String filename = "";
        try {
            // generate KeyStore file with privateKey and pass
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
            String privateKey = okc.getPrivateKeyFromKeyStore(filename, password);
            Assert.assertEquals(privateKey, PRIVATEKEY);
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
    public void sendSendTransactionTest() throws IOException {
        BuildTransaction.setMode("block");
        OKEXChainClient okc = generateClient();
        // okc instance contain url and backend
        AccountInfo account = generateAccountInfo(okc);

        String to = "okexchain1wq0zdnrc0r9uvqsly6622f4erl5qxly24qd4ur";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom(BASE_COIN_NAME);
        amount.setAmount("16.00000000");
        amountList.add(amount);
        // account instance is client infomation（get publicKey and address with privateKey,then query infomation from mainnet）
        // to is transfer to OKEXChain address
        // amountList is transfer array
        // resJson instance is response msg from mainnet
        JSONObject resJson = okc.sendSendTransaction(account, to, amountList, memo);
        Assert.assertNotNull(resJson);
        System.out.println(resJson);
        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
    }

    @Test
    public void sendCancelOrderTransactionTest() throws IOException {
        BuildTransaction.setMode("block");
        OKEXChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String side = "BUY";
        String product = TEST_PRODUCT;
        String price = "0.10000000";
        String quantity = "1.00000000";
        String memo = "";

        RequestPlaceOrderParams parms = new RequestPlaceOrderParams(price, product, quantity, side);

        JSONObject resJson = okc.sendPlaceOrderTransaction(account, parms, memo);
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));

        String orderId = StrUtils.getOrderIdFromResult(resJson);
        account.setSequenceNumber(
                Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
        JSONObject resJson2 = okc.sendCancelOrderTransaction(account, orderId, memo);
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
    public void sendMultiSendTransactionTest() throws IOException {
        BuildTransaction.setMode("block");
        OKEXChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String to = "okexchain1wq0zdnrc0r9uvqsly6622f4erl5qxly24qd4ur";
        String to2 = "okexchain1wq0zdnrc0r9uvqsly6622f4erl5qxly24qd4ur";
        String memo = "";

        String amount = "1.00000000";
        String denom = BASE_COIN_NAME;

        List<TransferUnit> transfers = new ArrayList<>();
        transfers.add(generateTrasferUnit(to, denom, amount));
        transfers.add(generateTrasferUnit(to2, denom, amount));

        JSONObject resJson = okc.sendMultiSendTransaction(account, transfers, memo);

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));
    }


    @Test
    public void sendCreateValidatorTransactionTest() throws IOException {
        OKEXChainClient okc = generateClient();
        BuildTransaction.setMode("block");
        AccountInfo account = generateAccountInfo(okc);

        String memo = "";
        Description description = new Description("m1", "1", "1", "1");
//        CommissionRates commission = new CommissionRates("0.10000000", "0.50000000", "0.00100000");
        CommissionRates commission = null;

        String delegatorAddress = account.getUserAddress();
        String validatorAddress = Crypto.generateValidatorAddressFromPub(account.getPublicKey());

        String pubKey = "okexchainvalconspub1zcjduepqfnlyuhtyefns3udmyjf28m4z5l2dr7pyplhmswq39uhw023u0kjsnl2u0t";
        Token minSelfDelegation = new Token();
        minSelfDelegation.setDenom(BASE_COIN_NAME);
        minSelfDelegation.setAmount("1000.00000000");

        JSONObject resJson = okc.sendCreateValidatorTransaction(account, description,
                commission, minSelfDelegation, delegatorAddress, validatorAddress, pubKey, memo);

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));

    }

    @Test
    public void sendEditValidatorTransactionTest() throws IOException {
        OKEXChainClient okc = generateClient();
        BuildTransaction.setMode("block");

        AccountInfo account = generateAccountInfo(okc);

        String memo = "";
        Description description = new Description("m1", "1", "1", "1");

        String validatorAddress = Crypto.generateValidatorAddressFromPub(account.getPublicKey());
        Assert.assertNotNull(validatorAddress);

        String minSelfDelegation = null;

        JSONObject resJson = okc.sendEditValidatorTransaction(account, minSelfDelegation, validatorAddress, description, memo);

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));

    }

    @Test
    public void sendVoteTransactionTest() throws IOException {
        OKEXChainClient okc = generateClient();
        BuildTransaction.setMode("block");
        AccountInfo account = generateAccountInfo(okc);

        String memo = "";

        String delegatorAddress = account.getUserAddress();
        String[] validatorAddress = {Crypto.generateValidatorAddressFromPub(account.getPublicKey())};

        String pubKey = "okexchainvalconspub1zcjduepqfnlyuhtyefns3udmyjf28m4z5l2dr7pyplhmswq39uhw023u0kjsnl2u0t";
        Token minSelfDelegation = new Token();
        minSelfDelegation.setDenom(BASE_COIN_NAME);
        minSelfDelegation.setAmount("1000.00000000");

        JSONObject resJson = okc.sendVoteTransaction(account, delegatorAddress, validatorAddress, memo);

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));

    }

    private OKEXChainClient generateClient() {
        OKEXChainClient okc = OKEXChainRestClientImpl.getOKEXChainClient(URL);
        return okc;
    }

    private AccountInfo generateAccountInfo(OKEXChainClient okc) {
        String privateKey = PRIVATEKEY;
        return okc.getAccountInfo(privateKey);
    }

    @Test
    public void getAccountALLTokensTest() {
        OKEXChainClient okc = generateClient();
        String show = "all";
        BaseModel resJson = okc.getAccountALLTokens(ADDRESS, show);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getAccountTokenTest() {
        OKEXChainClient okc = generateClient();
        String symbol = BASE_COIN_NAME;
        BaseModel resJson = okc.getAccountToken(ADDRESS, symbol);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTokensTest() {
        OKEXChainClient okc = generateClient();
        BaseModel resJson = okc.getTokens();
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTokenTest() {
        OKEXChainClient okc = generateClient();
        String symbol = BASE_COIN_NAME;
        BaseModel resJson = okc.getToken(symbol);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getProductsTest() {
        OKEXChainClient okc = generateClient();
        BaseModel resJson = okc.getProducts();
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getDepthBookTest() {
        OKEXChainClient okc = generateClient();
        String product = TEST_PRODUCT;
        BaseModel resJson = okc.getDepthBook(product);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getCandlesTest() {
        OKEXChainClient okc = generateClient();
        String granularity = "60";
        String instrumentId = TEST_PRODUCT;
        String size = "10";
        BaseModel resJson = okc.getCandles(granularity, instrumentId, size);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTickersTest() {
        OKEXChainClient okc = generateClient();
        String count = "10";
        BaseModel resJson = okc.getTickers(count);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getOrderListOpenTest() {
        OKEXChainClient okc = generateClient();
        String address = ADDRESS;
        RequestOrderListOpenParams params = new RequestOrderListOpenParams(address);
        BaseModel resJson = okc.getOrderListOpen(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getOrderListClosedTest() {
        OKEXChainClient okc = generateClient();
        RequestOrderListClosedParams params = new RequestOrderListClosedParams(ADDRESS);
        BaseModel resJson = okc.getOrderListClosed(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getDealsTest() {
        OKEXChainClient okc = generateClient();
        RequestDealsParams params = new RequestDealsParams(ADDRESS);
        BaseModel resJson = okc.getDeals(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTransactionsTest() {
        OKEXChainClient okc = generateClient();
        RequestTransactionsParams params = new RequestTransactionsParams(ADDRESS);
        BaseModel resJson = okc.getTransactions(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }
}
