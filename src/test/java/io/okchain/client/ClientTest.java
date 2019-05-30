package io.okchain.client;

import com.alibaba.fastjson.JSONObject;
import io.okchain.client.impl.OKChainClientImpl;
import io.okchain.types.AccountInfo;
import io.okchain.types.PlaceOrderRequestParms;
import io.okchain.types.Token;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClientTest {
    @Test
    public void testSendTransferTransaction() {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String to = "okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n";
        String memo = "";

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);

        JSONObject resJson = okc.sendSendTransaction(account, to, amountList, memo);
        System.out.println(resJson.toString());

    }

    @Test
    public void testSendOrderTransaction() {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String side = "BUY";
        String product = "xxb_okb";
        String price = "0.10000000";
        String quantity = "1.00000000";
        String memo = "";
        PlaceOrderRequestParms parms = new PlaceOrderRequestParms(price, product, quantity, side);

        JSONObject resJson = okc.sendPlaceOrderTransaction(account, parms, memo);
        System.out.println(resJson.toString());


        String orderId = (String) resJson.getJSONArray("tags").getJSONObject(1).get("value");
        account.setSequenceNumber(Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
        JSONObject resJson2 = okc.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(resJson2.toString());
    }

    @Test
    public void testCancelOrderTransaction() {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);

        String orderId = "ID0000029813-1";
        String memo = "";

        JSONObject resJson = okc.sendCancelOrderTransaction(account, orderId, memo);
        System.out.println(resJson.toString());
    }


    private OKChainClient generateClient() {
        String url = "http://192.168.71.35:1317";
        OKChainClient okc = OKChainClientImpl.getOKChainClient(url);
        return okc;
    }

    private AccountInfo generateAccountInfo(OKChainClient okc) {
        String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
        return okc.getAccountInfo(privateKey);
    }
}
