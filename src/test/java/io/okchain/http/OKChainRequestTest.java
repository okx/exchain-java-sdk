package io.okchain.http;

import io.okchain.api.transaction.BuildTransaction;
import io.okchain.client.OKChainClient;
import org.junit.Test;

public class OKChainRequestTest {
    @Test
    public void testGetAccount() {
        OKChainClient okc = generateClient();
        String  res = OKChainRequest.GetAccount(okc);
        System.out.println(res);
    }

    @Test
    public void testGetSequence() {
        OKChainClient okc = generateClient();
        String  res = OKChainRequest.GetSequance(okc);
        System.out.println(res);
    }

    @Test
    public  void testSendTransaction() {
        OKChainClient okc = generateClient();
        String side = "BUY";
        String product = "xxb_okb";
        String price = "1.00000000";
        String quantity = "1.00000000";
        String memo = "";
        String transaction = BuildTransaction.generatePlaceOrderTransaction(okc,side,product,price,quantity,memo);
        System.out.println(transaction);
        String res  = OKChainRequest.SendTransaction(okc,transaction);
        System.out.println(res);
    }

    private OKChainClient generateClient() {
        String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
        //String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
        String url = "http://192.168.71.35:1317";
        OKChainClient okc = new OKChainClient(privateKey,url);
        return okc;
    }
}
