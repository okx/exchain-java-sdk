package com.okchain.client;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.okchain.client.impl.OKChainClientImpl;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.common.ConstantIF;
import com.okchain.crypto.AddressUtil;
import com.okchain.encoding.EncodeUtils;
import com.okchain.encoding.message.AminoEncode;
import com.okchain.proto.Transfer;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.*;
import com.okchain.encoding.message.MessageType;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Base64;
import java.util.List;

public class OKChainRPCClientImplTest {
    private static String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
    private static String url_rpc = "http://localhost:26657";//rpc
    private static String url_rest = "http://localhost:26659";//rest

    @Test
    public void sendRawBytes() {
        String data = "b401f0625dee0a42c9ee213f0a148c88be1b29942b78c801b43a77dd1050f7ee316512145ab0c4b287f6aa0ab9bf27812351aa11cbfcb98e1a100a036f6b621209313030303030303030126a0a26eb5ae9872102f0fbeb512118816cc20854d6dc45f8e3094d580dfa8252573f0329278fc7a3c41240d7a28eda2aac0d0d9cddd5f7972c5b23a3d9bbe87d8fde95b68abe7099d7ae4064813d6960ea6b6f623244a08181736eb71009191acc8ab6772752489790817a";
        byte[] transactionData = Hex.decode(data);
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);

        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String res = client.sendTransaction(transactionData);
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

        String res = client.sendTransaction(transactionData);
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
        Token amount = new Token("5", "okb");
        amountList.add(amount);
        byte[] txRawBytes = BuildTransaction.generateAminoSendTransaction(account, to, amountList, memo);
        String res = client.sendTransaction(txRawBytes);
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
        String ret = client.sendSendTransaction(account, to, amountList, memo);
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
        String ret = client.sendPlaceOrderTransaction(account, side, product, price, quantity, memo);
        System.out.println(ret);
    }

    @Test
    public void testSendCancelOrderTransaction() throws IOException {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url_rpc);
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        String orderId = "ID0000065785-1";
        String memo = "";
        String ret = client.sendCancelOrderTransaction(account, orderId, memo);
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
        String ret = client.sendMultiSendTransaction(account, transferUnits, memo);
        System.out.println(ret);
    }
}