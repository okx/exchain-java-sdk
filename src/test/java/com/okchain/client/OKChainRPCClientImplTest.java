package com.okchain.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.impl.OKChainClientImpl;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.encoding.EncodeUtils;
import com.okchain.encoding.message.MessageType;
import com.okchain.proto.Transfer;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class OKChainRPCClientImplTest {
    private static String privateKey = "de0e9d9e7bac1366f7d8719a450dab03c9b704172ba43e0a25a7be1d51c69a87";
    private static String url_rpc = "http://localhost:26657";//rpc
    private static String url_rest = "http://localhost:26659";//rest


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
    public void sendRawBytes() {
        String data = "b401f0625dee0a42c9ee213f0a148c88be1b29942b78c801b43a77dd1050f7ee316512145ab0c4b287f6aa0ab9bf27812351aa11cbfcb98e1a100a036f6b621209313030303030303030126a0a26eb5ae9872102f0fbeb512118816cc20854d6dc45f8e3094d580dfa8252573f0329278fc7a3c41240d7a28eda2aac0d0d9cddd5f7972c5b23a3d9bbe87d8fde95b68abe7099d7ae4064813d6960ea6b6f623244a08181736eb71009191acc8ab6772752489790817a";
        byte[] transactionData = Hex.decode(data);
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);

        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        JSONObject res = client.sendTransaction(transactionData);
        System.out.println(res);
    }

    @Test
    public void sendTransaction() {
        String data = "tAHwYl3uCkI8Ht70ChSxLq0NxypP2pN1DUqKCoXeJPNCshImChT0uZ4HU4lve6xWlR9H8BEW8ZwDABIOCgNva2ISBzIyNTAwMDASagom61rphyED1C8GqfBLEgskTUfp2VjnZfYGiojlJuIBMJvj1XcLWd4SQLhEzVhqEyQE4JwkZojT7zMcHqYdtdqK6roZKvEN8gkLbKPqCTTn+bsmOcb4qr1BNe0/rVyxu14vQ6QTvT2UpQw=";
        byte[] transactionData = Base64.getDecoder().decode(data);
        //System.out.println(Hex.toHexString(transactionData));


        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);

        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        JSONObject res = client.sendTransaction(transactionData);
        System.out.println(res);
    }

//    michael.w added 20190711

    @Test
    public void testAminoSendTransaction() throws IOException {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        System.out.println(account.getUserAddress());
        // 收款人
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";
        List<Token> amountList = new ArrayList<>();
        Token amount = new Token("5.00000000", "okb");
        amountList.add(amount);
        byte[] txRawBytes = BuildTransaction.generateAminoSendTransaction(account, to, amountList, memo);
        JSONObject res = client.sendTransaction(txRawBytes);
        System.out.println(res);
    }

    @Test
    public void testAminoBaseType() throws IOException {
        Transfer.BaseType btProto = Transfer.BaseType.newBuilder()
                .setI(500000000)
                .setS("OKcoin")
                .addSs("btc").addSs("okb")
                .addStus(Transfer.Stu.newBuilder().setId(500000000).build())
                .addStus(Transfer.Stu.newBuilder().setId(500000000).build())
                .build();
        System.out.println(btProto);

        byte[] rawBytesProtoEncoded = EncodeUtils.aminoWrap(btProto.toByteArray(), MessageType.BaseType.getTypePrefixBytes(), true);
        System.out.println(Hex.toHexString(rawBytesProtoEncoded));

    }

    @Test
    public void testBigInt() throws IOException {
        Transfer.Stu stuProto = Transfer.Stu.newBuilder().setId(500000000).build();
        byte[] rawBytesProtoEncoded = EncodeUtils.aminoWrap(stuProto.toByteArray(), MessageType.BaseType.getTypePrefixBytes(), true);
        System.out.println(Hex.toHexString(rawBytesProtoEncoded));
        String str = EncodeUtils.stringTo8("5.00112212121212");
        System.out.println(str);
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

    private byte[] encodePubkey(byte[] pubkeyRawBytes) {
        byte[] pubKeyPrefix = MessageType.PubKey.getTypePrefixBytes();
        byte[] pubKeyAminoEncoded = new byte[pubkeyRawBytes.length + pubKeyPrefix.length + 1];
        // 从pubKeyPrefix的索引0开始向pubKeyAminoEncoded的索引0位置开始复制长度为pubKeyPrefix.length个字节
        System.arraycopy(pubKeyPrefix, 0, pubKeyAminoEncoded, 0, pubKeyPrefix.length);
        pubKeyAminoEncoded[pubKeyPrefix.length] = (byte) 33;
        System.arraycopy(pubkeyRawBytes, 0, pubKeyAminoEncoded, pubKeyPrefix.length + 1, pubkeyRawBytes.length);
        return pubKeyAminoEncoded;
    }

    // michael.w added 20190715
    @Test
    public void testSendSendTransaction() throws NullPointerException, IOException {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";
        List<Token> amountList = new ArrayList<>();
        Token amount = new Token("1.00000000", "okb");
        amountList.add(amount);
        JSONObject ret = client.sendSendTransaction(account, to, amountList, memo);
        System.out.println(ret);
    }

    @Test
    public void testSendPlaceOrderTransaction() throws IOException {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        String side = "BUY";
        String product = "xxb_okb";
        String price = "1.123456789";
        String quantity = "1.00000000";
        String memo = "";
        RequestPlaceOrderParams parms = new RequestPlaceOrderParams(price, product, quantity, side);
        JSONObject ret = client.sendPlaceOrderTransaction(account, parms, memo);
        System.out.println(ret);
    }

    @Test
    public void testSendCancelOrderTransaction() throws IOException {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        String orderId = "ID0000065785-1";
        String memo = "";
        JSONObject ret = client.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(ret);
    }

    @Test
    public void testSendMultiSendTransaction() throws IOException {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        List<TransferUnit> transferUnits = new ArrayList<>();
        // create the 1st tx
        String to1 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";
        List<Token> amounts1 = new ArrayList<>();
        amounts1.add(new Token("1.000000", "okb"));
        amounts1.add(new Token("5.500000", "okb"));
        transferUnits.add(new TransferUnit(amounts1, to1));
        // create the 2nd tx
        String to2 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        List<Token> amounts2 = new ArrayList<>();
        amounts2.add(new Token("10.000000", "okb"));
        amounts2.add(new Token("50.000000", "okb"));
        transferUnits.add(new TransferUnit(amounts2, to2));
        JSONObject ret = client.sendMultiSendTransaction(account, transferUnits, memo);
        System.out.println(ret);
    }

    // added by michael.w 20190729
    String Mnemo1 = "sustain hole urban away boy core lazy brick wait drive tiger tell";
    String Name1 = "michael.w";
    String Passwd1 = "12345678";
    String Addr1 = "okchain1mm43akh88a3qendlmlzjldf8lkeynq68r8l6ts";

    String Mnemo2 = "exotic general brisk blind beach unveil license emerge fee demand giraffe clinic";
    String Name2 = "songyao";
    String Passwd2 = "12345678";
    String Addr2 = "okchain1qsc6ckz6uvrqdxg36p6rrwp25kyg4dtl8gdr92";

    String Mnemo3 = "pulp promote shaft trial float patch quantum scene jealous reflect famous govern";
    String Name3 = "2pac";
    String Passwd3 = "12345678";
    String Addr3 = "okchain1lx7udcmzx6lzuvpk6sdjlasz00p7jgwhfyx3dt";

    // okchaincli tx send okchain1mm43akh88a3qendlmlzjldf8lkeynq68r8l6ts 10000okb --from jack -y
    // 发新币
    // okchaincli tx token issue -s gyc --desc "the coin of the coinless world" --total-supply 10000000 -w gyc --mintable true --from michael.w

    @Test
    public void testCreateAddressInfo() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);

        AddressInfo addrInfo = client.createAddressInfo();
        System.out.println(addrInfo.getPrivateKey());
        System.out.println(addrInfo.getPublicKey());
        System.out.println(addrInfo.getUserAddress());
        Assert.assertNotNull(addrInfo);
    }

    @Test
    public void testGenerateMnemonic() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        String Mnemonic = client.generateMnemonic();
        System.out.println(Mnemonic);
        Assert.assertNotNull(Mnemonic);

    }

    @Test
    public void testGetPrivateKeyFromMnemonic() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        String privKey = client.getPrivateKeyFromMnemonic(Mnemo1);
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
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        JSONObject jo = client.getAccountFromNode(Addr1);
        System.out.println(jo);
        Assert.assertFalse(jo.containsKey("code"));
    }

    @Test
    public void testGetAccountALLTokens() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getAccountALLTokens(Addr1, "all");
        System.out.println(bm);
        Assert.assertNotNull(bm.getData());
    }

    @Test
    public void testGetAccountToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getAccountToken(Addr1, "gyc-779");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetTokens() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getTokens();
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getToken("gyc-779");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetProducts() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getProducts();
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetDepthBook() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getDepthBook("xxb_okb");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetCandles() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getCandles("60", "xxb_okb", "100");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetTickers() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        BaseModel bm = client.getTickers("10");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetMatches() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        // get the system time right now and convert it to String
        String nowTimeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        BaseModel bm = client.getMatches("xxb_okb", "0", nowTimeStamp, "0", "10");
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetOrderListOpen() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        String product = "xxb_okb";
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestOrderListOpenParams olop = new RequestOrderListOpenParams(product, Addr1, start, end, side, page, perPage);
        BaseModel bm = client.getOrderListOpen(olop);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    // added by michael.w 20190730
    @Test
    public void testGetOrderListClosed() {
        // cancel a order first by okchaincli
        // okchaincli tx order cancel ID0000029153-1 --from michael.w
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        String product = "xxb_okb";
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        // if input hideNofill is not "true", we always treat it as "false"
        String hideNoFill = "false";
        RequestOrderListClosedParams olcp = new RequestOrderListClosedParams(product, Addr1, start, end, side, page, perPage, hideNoFill);
        BaseModel bm = client.getOrderListClosed(olcp);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetDeals() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        String product = "xxb_okb";
        String side = "BUY";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestDealsParams rdp = new RequestDealsParams(product, Addr1, start, end, side, page, perPage);
        BaseModel bm = client.getDeals(rdp);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

    @Test
    public void testGetTransactions() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        String type = "1";
        String start = "1";
        // get the system time right now and convert it to String
        String end = String.valueOf(System.currentTimeMillis() / 1000);
        String page = "0";
        String perPage = "10";
        RequestTransactionsParams rtp = new RequestTransactionsParams(Addr1, type, start, end, page, perPage);
        BaseModel bm = client.getTransactions(rtp);
        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }

}