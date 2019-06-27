package com.okchain.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okchain.client.impl.OKChainClientImpl;
import com.okchain.common.ConstantIF;
import com.okchain.common.HttpUtils;
import com.okchain.types.AccountInfo;
import com.okchain.types.AddressInfo;
import com.okchain.types.Token;
import com.okchain.types.TransferUnit;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BenchMarkTest {
    private static String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
    private static String url = "http://127.0.0.1:26659";
    private static String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
    private static String mnemonic = "total lottery arena when pudding best candy until army spoil drill pool";
    List<AddressInfo> testAddrList = new ArrayList<>();

    @Test
    public void sendSendTransaction() {
        OKChainClient okc = generateClient();
        AccountInfo account = generateAccountInfo(okc);
        //List<AddressInfo> testAddrList = new ArrayList<>();
        int testNum = 10;
        int multiSendNum = 1000;
        for (int i = 0; i < testNum; i++) {
            List<TransferUnit> transfers = new ArrayList<>();
            for (int j = 0; j < multiSendNum; j++) {
                AddressInfo addressInfo = okc.createAddressInfo();
                testAddrList.add(addressInfo);
                String to = addressInfo.getUserAddress();
                transfers.add(generateTrasferUnit(to, "okb", "1.00000000"));
            }
            String memo = "";
            JSONObject resJson = okc.sendMultiSendTransaction(account, transfers, memo);
            //System.out.println(resJson.toString());
            Assert.assertEquals(true, resJson.getJSONArray("logs").getJSONObject(0).get("success"));
            System.out.println("done " + i);
            account.setSequenceNumber(Integer.toString(Integer.parseInt(account.getSequenceNumber()) + 1));
        }
        ExecutorService exec = Executors.newFixedThreadPool(20);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testNum * multiSendNum; i++) {
            //System.out.println(i);
            AddressInfo addressInfo = testAddrList.get(i);
            String myurl = url + ConstantIF.ACCOUNT_URL_PATH + addressInfo.getUserAddress();
            exec.submit(new WorkTask(myurl));
        }

        exec.shutdown();
        try {
            if (!exec.awaitTermination(3600, TimeUnit.SECONDS)) {//20S
                System.out.println(" 到达指定时间，还有线程没执行完，不再等待，关闭线程池!");
                exec.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        long usedTime = (endTime - startTime) / 1000;
        System.out.println("usedTime:" + usedTime + "s");
        System.out.println("RPS:" + testNum * multiSendNum / usedTime);

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

    private OKChainClient generateClient() {
        String url = this.url;
        OKChainClient okc = OKChainClientImpl.getOKChainClient(url);
        return okc;
    }

    private AccountInfo generateAccountInfo(OKChainClient okc) {
        String privateKey = this.privateKey;
        return okc.getAccountInfo(privateKey);
    }
}


class WorkTask implements Runnable {
    public WorkTask(String url) {
        this.url = url;
    }

    public String url;

    public void run() {
        //System.out.println("GET " + url);
        String res = HttpUtils.httpGet(url);
        try {
            JSONObject accountJson = JSON.parseObject(res);
            String sequence = (String) accountJson.getJSONObject("value").get("sequence");
        } catch (Exception e) {
            System.out.println("err1");
        }

    }
}
