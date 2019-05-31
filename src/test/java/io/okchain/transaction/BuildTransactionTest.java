package io.okchain.transaction;

import io.okchain.client.OKChainClient;
import io.okchain.client.impl.OKChainClientImpl;
import io.okchain.types.AccountInfo;
import io.okchain.types.AddressInfo;
import io.okchain.types.Token;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BuildTransactionTest {
    @Test
    public void testBuildNewOrderTx() {
        AccountInfo account = generateAccountInfo();
        String sequence = "50";
        String side = "BUY";
        String product = "xxb_okb";
        String price = "1.00000000";
        String quantity = "1.00000000";
        String memo = "";
        String transaction = BuildTransaction.generatePlaceOrderTransaction(account, side, product, price, quantity, memo);
        System.out.println(transaction);
    }

    @Test
    public void testBuildCancelOrderTx() {
        AccountInfo account = generateAccountInfo();

        String sequence = "51";
        String orderId = "ID0000065785-1";
        String memo = "";
        String transaction = BuildTransaction.generateCancelOrderTransaction(account, orderId, memo);
        System.out.println(transaction);
    }

    @Test
    public void testBuildSendTx() {
        AccountInfo account = generateAccountInfo();

        String sequence = "52";
        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);

        String transaction = BuildTransaction.generateSendTransaction(account, to, amountList, memo);
        System.out.println(transaction);
    }


    private AccountInfo generateAccountInfo() {
        String url = "";
        OKChainClient okc = OKChainClientImpl.getOKChainClient(url);
        AddressInfo addressInfo = okc.createAddressInfo();

        return new AccountInfo(addressInfo, "0", "0");
    }
}