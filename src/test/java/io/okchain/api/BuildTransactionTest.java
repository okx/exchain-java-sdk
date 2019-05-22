package io.okchain.api;

import io.cosmos.types.Token;
import io.okchain.api.transaction.BuildTransaction;
import io.okchain.client.OKChainClient;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BuildTransactionTest {
    @Test
    public void testBuildNewOrderTx() {
        OKChainClient okc = generateClient();
        String sequence = "50";
        String side = "BUY";
        String product = "xxb_okb";
        String price = "1.00000000";
        String quantity = "1.00000000";
        String memo = "";
        String transaction = BuildTransaction.generatePlaceOrderTransaction(okc,side,product,price,quantity,memo,sequence);
        System.out.println(transaction);
    }

    @Test
    public void testBuildCancelOrderTx() {
        OKChainClient okc = generateClient();
        String sequence = "51";
        String orderId = "ID0000065785-1";
        String memo = "";
        String transaction = BuildTransaction.generateCancelOrderTransaction(okc,orderId,memo,sequence);
        System.out.println(transaction);
    }

    @Test
    public void testBuildSendTx() {
        OKChainClient okc = generateClient();
        String sequence = "52";
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);

        String transaction = BuildTransaction.generateSendTransaction(okc,to,amountList,memo,sequence);
        System.out.println(transaction);
    }

    private OKChainClient generateClient() {
        String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
        String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        int accountNumber = 4;
        OKChainClient okc = new OKChainClient(privateKey,address,accountNumber);
        return okc;
    }
}