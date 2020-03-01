package com.okchain.transaction;

import com.okchain.client.OKChainClient;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.common.ConstantIF;
import com.okchain.common.jsonrpc.JSONRPCUtils;
import com.okchain.types.AccountInfo;
import com.okchain.types.Token;
import com.okchain.types.TransferUnit;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BuildTransactionTest {
    private static String rpcUrl = "http://3.13.150.20:26657";
    private static String privateKey = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";

    @Test
    public void testBuildAminoNewOrderTx() throws IOException {
        AccountInfo account = generateAccountInfoByRpc();
        String side = "BUY";
        String product = "tokt_tusdk";
        String price = "1.00000000";
        String quantity = "1.00000000";
        String memo = "";
        byte[] tx = BuildTransaction.generateAminoPlaceOrderTransaction(account, side, product, price, quantity, memo);
        String method = ConstantIF.RPC_METHOD_TX_SEND_ASYNC;
        Map<String, Object> mp = new TreeMap<>();
        mp.put("tx", tx);
        String res = JSONRPCUtils.getRpcSendData(method, mp);
        System.out.println(res);
    }

    @Test
    public void testBuildAminoCancelOrderTx() throws IOException {
        AccountInfo account = generateAccountInfoByRpc();

        String orderId = "ID0000065785-1";
        String memo = "";
        byte[] tx = BuildTransaction.generateAminoCancelOrderTransaction(account, orderId, memo);
        String method = ConstantIF.RPC_METHOD_TX_SEND_ASYNC;
        Map<String, Object> mp = new TreeMap<>();
        mp.put("tx", tx);
        String res = JSONRPCUtils.getRpcSendData(method, mp);
        System.out.println(res);
    }

    @Test
    public void testBuildAminoSendTx() throws IOException {
        AccountInfo account = generateAccountInfoByRpc();

        String sequence = "52";
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);
        byte[] tx = BuildTransaction.generateAminoSendTransaction(account, to, amountList, memo);

        String method = ConstantIF.RPC_METHOD_TX_SEND_ASYNC;
        Map<String, Object> mp = new TreeMap<>();
        mp.put("tx", tx);
        String res = JSONRPCUtils.getRpcSendData(method, mp);
        System.out.println(res);
    }


    @Test
    public void testBuildAminoMultiSendTx() throws IOException {
        AccountInfo account = generateAccountInfoByRpc();
        List<TransferUnit> transfers = new ArrayList<>();
        String memo = "";

        // create 1st tx
        String to1 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        List<Token> amountList1 = new ArrayList<>();
        Token amount11 = new Token("10.00000000", "okb");
        Token amount12 = new Token("5.55500000", "btc");
        amountList1.add(amount11);
        amountList1.add(amount12);
        TransferUnit tu1 = new TransferUnit(amountList1, to1);
        transfers.add(tu1);

        // create 2nd tx
        List<Token> amountList2 = new ArrayList<>();
        String to2 = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";

        Token amount21 = new Token("9.999000", "bnb");
        Token amount22 = new Token("5.555000", "eth");
        Token amount23 = new Token("44.444444", "btc");
        amountList2.add(amount21);
        amountList2.add(amount22);
        amountList2.add(amount23);
        TransferUnit tu2 = new TransferUnit(amountList2, to2);
        transfers.add(tu2);
        byte[] tx = BuildTransaction.generateAminoMultiSendTransaction(account, transfers, memo);


        String method = ConstantIF.RPC_METHOD_TX_SEND_ASYNC;
        Map<String, Object> mp = new TreeMap<>();
        mp.put("tx", tx);
        String res = JSONRPCUtils.getRpcSendData(method, mp);
        System.out.println(res);
    }

    private AccountInfo generateAccountInfoByRpc() {
        String url = rpcUrl;
        OKChainClient okc = OKChainRPCClientImpl.getOKChainClient(url);
        return okc.getAccountInfo(privateKey);
    }
}