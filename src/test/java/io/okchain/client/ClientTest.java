package io.okchain.client;

import io.cosmos.types.Token;
import org.bouncycastle.util.test.TestResult;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    @Test
    public void testSendTransferTransaction() {
        OKChainClient okc = generateClient();

        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);

        String res = okc.sendTransferTransaction(to,amountList,memo);
        System.out.println(res);

    }

    @Test
    public void testSendPlaceOrderTransaction() {
        OKChainClient okc = generateClient();

        String side = "BUY";
        String product = "xxb_okb";
        String price = "1.00000000";
        String quantity = "1.00000000";
        String memo = "";

        String res = okc.sendPlaceOrderTransaction(side,product,price,quantity,memo);
        System.out.println(res);
    }

    @Test
    public void testCancelOrderTransaction() {
        OKChainClient okc = generateClient();

        String orderId = "ID0000017570-1";
        String memo = "";

        String res = okc.sendCancelOrderTransaction(orderId,memo);
        System.out.println(res);
    }

    private OKChainClient generateClient() {
        String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
        String url = "http://192.168.71.35:1317";
        OKChainClient okc = new OKChainClient(privateKey,url);
        return okc;
    }
}
