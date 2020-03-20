package com.okchain.transaction;

import com.okchain.client.OKChainClient;
import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.client.impl.OKChainRestClientImpl;
import com.okchain.common.ConstantIF;
import com.okchain.common.jsonrpc.JSONRPCUtils;
import com.okchain.types.AccountInfo;
import com.okchain.types.AddressInfo;
import com.okchain.types.Token;
import com.okchain.types.TransferUnit;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BuildTransactionTest {

    private static String PRIVATEKEY = "29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884";

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
        Assert.assertNotNull(res);
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
        Assert.assertNotNull(res);
    }

    private AccountInfo generateAccountInfoByRpc() {
        AccountInfo accountInfo = new AccountInfo(PRIVATEKEY, "0206b93fb4d6a2aabee9c9b13931e18fdb4ba859473652e958c11d01f1dca24a7e", "okchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptdqket5k", "2741", "27");

        OKChainClient okc = mock(OKChainClient.class);
        when(okc.getAccountInfo(PRIVATEKEY)).thenReturn(accountInfo);
        return okc.getAccountInfo(PRIVATEKEY);
    }



    @Test
    public void testBuildSendTx() {
        AccountInfo account = generateAccountInfo();
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("5.00000000");
        amountList.add(amount);

        String transaction = BuildTransaction.generateSendTransaction(account, to, amountList, memo);
        Assert.assertNotNull(transaction);
    }

    private AccountInfo generateAccountInfo() {
        String url = "";
        OKChainClient okc = OKChainRestClientImpl.getOKChainClient(url);
        return okc.createAccount();
    }

}