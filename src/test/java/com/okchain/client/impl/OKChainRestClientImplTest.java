package com.okchain.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.OKChainClient;
import com.okchain.client.impl.OKChainRestClientImpl;
import com.okchain.common.StrUtils;
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

    // privateKey's address is okchain1v853tq96n9ghvyxlvqyxyj97589clccr33yr7a
    private static String PRIVATEKEY = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";
    // rest service port is 26659
    private static String URL = "http://127.0.0.1:26659";
    private static String ADDRESS = "okchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptdqket5k";
    private static String MNEMONIC = "total lottery arena when pudding best candy until army spoil drill pool";

    private static String TEST_COIN_NAME = "xxb-3fc";
    private static String BASE_COIN_NAME = "okt";
    private static String TEST_PRODUCT = TEST_COIN_NAME + "_" + BASE_COIN_NAME;

    @Test
    public void getAddressInfoTest() {
        OKChainClient okc = generateClient();
        // get publicKey and address with privateKey
        AddressInfo addressInfo = okc.getAddressInfo(PRIVATEKEY);
        Assert.assertNotNull(addressInfo);
        Assert.assertEquals(ADDRESS, addressInfo.getUserAddress());
        Assert.assertNotNull(addressInfo.getPrivateKey());
        Assert.assertNotNull(addressInfo.getPublicKey());
    }

    @Test
    public void getAccountInfoTest() {
        OKChainClient okc = generateClient();
        AccountInfo accountInfo = okc.getAccountInfo(PRIVATEKEY);
        System.out.println(accountInfo);
        Assert.assertNotNull(accountInfo);
        Assert.assertNotNull(accountInfo.getSequenceNumber());
        Assert.assertNotNull(accountInfo.getAccountNumber());
    }

    @Test
    // get privateKey with mnemonic
    public void getPrivateKeyFromMnemonicTest() {
        OKChainClient okc = generateClient();
        String privateKey = okc.getPrivateKeyFromMnemonic(MNEMONIC);
        Assert.assertEquals(PRIVATEKEY, privateKey);
    }

    @Test
    public void generateMnemonicTest() {
        OKChainClient okc = generateClient();
        // create mnemonic
        String mnemonic = okc.generateMnemonic();
        String[] words = mnemonic.split(" ");
        Assert.assertEquals(12, words.length);
    }

    @Test
    public void getPrivateKeyFromKeyStoreTest() {
        OKChainClient okc = generateClient();
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
        OKChainClient okc = generateClient();
        // okc instance contain url and backend
        AccountInfo account = generateAccountInfo(okc);

        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom(BASE_COIN_NAME);
        amount.setAmount("16.00000000");
        amountList.add(amount);
        // account instance is client infomation（get publicKey and address with privateKey,then query infomation from mainnet）
        // to is transfer to OKChain address
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
        OKChainClient okc = generateClient();
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
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String to2 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
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
        OKChainClient okc = generateClient();
        BuildTransaction.setMode("block");
        AccountInfo account = generateAccountInfo(okc);

        String memo = "";
        Description description = new Description("m1", "1", "1", "1");
        CommissionRates commission = new CommissionRates("0.10000000", "0.50000000", "0.00100000");
        String delegatorAddress = account.getUserAddress();
        String validatorAddress = Crypto.generateValidatorAddressFromPub(account.getPublicKey());

        String pubKey = "okchainvalconspub1zcjduepqtv2yy90ptjegdm34vfhlq2uw9eu39hjrt98sffj7yghl4s47xv7swuf0dx";
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
        OKChainClient okc = generateClient();
        BuildTransaction.setMode("block");

        AccountInfo account = generateAccountInfo(okc);

        String memo = "";
        Description description = new Description("m1", "1", "1", "1");

        String validatorAddress = Crypto.generateValidatorAddressFromPub(account.getPublicKey());
        Assert.assertNotNull(validatorAddress);

        String minSelfDelegation = "1100";

        JSONObject resJson = okc.sendEditValidatorTransaction(account, minSelfDelegation, validatorAddress, description, memo);

        Object code = resJson.get("code");
        Object err = resJson.get("error");
        Assert.assertNull(code);
        Assert.assertNull(err);
        Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));

    }

    private OKChainClient generateClient() {
        OKChainClient okc = OKChainRestClientImpl.getOKChainClient(URL);
        return okc;
    }

    private AccountInfo generateAccountInfo(OKChainClient okc) {
        String privateKey = PRIVATEKEY;
        return okc.getAccountInfo(privateKey);
    }

    @Test
    public void getAccountALLTokensTest() {
        OKChainClient okc = generateClient();
        String show = "all";
        BaseModel resJson = okc.getAccountALLTokens(ADDRESS, show);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getAccountTokenTest() {
        OKChainClient okc = generateClient();
        String symbol = BASE_COIN_NAME;
        BaseModel resJson = okc.getAccountToken(ADDRESS, symbol);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTokensTest() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getTokens();
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTokenTest() {
        OKChainClient okc = generateClient();
        String symbol = BASE_COIN_NAME;
        BaseModel resJson = okc.getToken(symbol);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getProductsTest() {
        OKChainClient okc = generateClient();
        BaseModel resJson = okc.getProducts();
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getDepthBookTest() {
        OKChainClient okc = generateClient();
        String product = TEST_PRODUCT;
        BaseModel resJson = okc.getDepthBook(product);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getCandlesTest() {
        OKChainClient okc = generateClient();
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
        OKChainClient okc = generateClient();
        String count = "10";
        BaseModel resJson = okc.getTickers(count);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getOrderListOpenTest() {
        OKChainClient okc = generateClient();
        String address = ADDRESS;
        RequestOrderListOpenParams params = new RequestOrderListOpenParams(address);
        BaseModel resJson = okc.getOrderListOpen(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getOrderListClosedTest() {
        OKChainClient okc = generateClient();
        RequestOrderListClosedParams params = new RequestOrderListClosedParams(ADDRESS);
        BaseModel resJson = okc.getOrderListClosed(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getDealsTest() {
        OKChainClient okc = generateClient();
        RequestDealsParams params = new RequestDealsParams(ADDRESS);
        BaseModel resJson = okc.getDeals(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }

    @Test
    public void getTransactionsTest() {
        OKChainClient okc = generateClient();
        RequestTransactionsParams params = new RequestTransactionsParams(ADDRESS);
        BaseModel resJson = okc.getTransactions(params);
        String res = JSON.toJSON(resJson).toString();
        Assert.assertNotNull(res);
        Assert.assertEquals(0, resJson.getCode());
    }
}
