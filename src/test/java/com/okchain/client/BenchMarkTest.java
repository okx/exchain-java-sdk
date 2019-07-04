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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BenchMarkTest {
    private static String privateKey = "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632";
    private static String url = "http://localhost:26659";
    private static String address = "okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf";
    private static String mnemonic = "total lottery arena when pudding best candy until army spoil drill pool";
    List<AddressInfo> testAddrList = new ArrayList<>();

    int numberofRPS = 0;
    String currentStep = "";
    String lastAccess = "";



    @Test
    public void TestQueryAccounts(){
        long startTimeQ = System.currentTimeMillis();
        HttpUtils.httpGet("http://localhost:26659/auth/accounts/okchain1kyh26rw89f8a4ym4p49g5z59mcj0xs4j045e39");
        long endTimeMultiQ = System.currentTimeMillis();
        System.out.println("Single times:" + (endTimeMultiQ - startTimeQ)+ "ms");

        for(int i = 0; i < 10000; i++){
            HttpUtils.httpGet("http://localhost:26659/auth/accounts/okchain1kyh26rw89f8a4ym4p49g5z59mcj0xs4j045e39");
        }
        endTimeMultiQ = System.currentTimeMillis();
        long usedTimeMultiQ = (endTimeMultiQ - startTimeQ) / 1000;

        System.out.println("Test Http Get Request:" + usedTimeMultiQ + "s");
        System.out.println("RPS:" + 10000 / usedTimeMultiQ);


        startTimeQ = System.currentTimeMillis();
        for(int i = 0; i < 10000; i++){
            String res = HttpUtils.httpGet("http://localhost:26659/auth/accounts/okchain1kyh26rw89f8a4ym4p49g5z59mcj0xs4j045e39");
        }
        endTimeMultiQ = System.currentTimeMillis();
        usedTimeMultiQ = (endTimeMultiQ - startTimeQ) / 1000;

        System.out.println("httpGet Test Http Get Request:" + usedTimeMultiQ + "s");
        System.out.println("httpGet RPS:" + 10000 / usedTimeMultiQ);
        return ;
    }

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


        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    System.out.println(currentStep + "'s RPS: " + numberofRPS);
                    if(numberofRPS == 0 && lastAccess != ""){
                        System.out.println("Timed out, hang at " + lastAccess);
                    }
                    numberofRPS = 0;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });



        currentStep = "Multi Account Test";
        t3.start();

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     //   ExecutorService exec = Executors.newFixedThreadPool(20);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < testNum * multiSendNum; i++) {
            //System.out.println(i);
            AddressInfo addressInfo = testAddrList.get(i);
            String myurl = url + ConstantIF.ACCOUNT_URL_PATH + addressInfo.getUserAddress();
          //  exec.submit(new WorkTask(myurl));
            lastAccess = myurl;
            String res = HttpUtils.httpGet(myurl);
            numberofRPS++;
        }

        /*exec.shutdown();
        try {
            if (!exec.awaitTermination(3600, TimeUnit.SECONDS)) {//20S
                System.out.println(" 到达指定时间，还有线程没执行完，不再等待，关闭线程池!");
                exec.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */


        System.out.println("begin test");


        long endTimeMulti = System.currentTimeMillis();
        long usedTimeMulti = (endTimeMulti - startTime) / 1000;
        System.out.println("Multi usedTime:" + usedTimeMulti + "s");
        System.out.println("Multi RPS:" + testNum * multiSendNum / usedTimeMulti);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      //  ExecutorService exec2 = Executors.newFixedThreadPool(20);
        startTime = System.currentTimeMillis();
        currentStep = "Multi Account Test Again";
        numberofRPS = 0;
        for (int i = 0; i < testNum * multiSendNum; i++) {
            //System.out.println(i);
            AddressInfo addressInfo = testAddrList.get(i);
            String myurl = url + ConstantIF.ACCOUNT_URL_PATH + addressInfo.getUserAddress();
           // exec2.submit(new WorkTask(myurl));
            lastAccess = myurl;
            String res = HttpUtils.httpGet(myurl);
            numberofRPS++;

        }

        /*exec2.shutdown();
        try {
            if (!exec2.awaitTermination(3600, TimeUnit.SECONDS)) {//20S
                System.out.println(" 到达指定时间，还有线程没执行完，不再等待，关闭线程池!");
                exec2.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */

        long endTimeMulti2 = System.currentTimeMillis();
        long usedTimeMulti2 = (endTimeMulti2 - startTime) / 1000;
        System.out.println("Multi usedTime Again:" + usedTimeMulti2 + "s");
        System.out.println("Multi RPS Again:" + testNum * multiSendNum / usedTimeMulti2);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //    ExecutorService exec3 = Executors.newFixedThreadPool(20);
        startTime = System.currentTimeMillis();

        currentStep = "Single Account Test";
        numberofRPS = 0;


        for (int i = 0; i < testNum * multiSendNum; i++) {
            //System.out.println(i);
            AddressInfo addressInfo = testAddrList.get(0);
            String myurl = url + ConstantIF.ACCOUNT_URL_PATH + addressInfo.getUserAddress();
          //  exec3.submit(new WorkTask(myurl));

            lastAccess = myurl;
            String res = HttpUtils.httpGet(myurl);
            numberofRPS++;

        }



    /*    exec3.shutdown();
        try {
            if (!exec3.awaitTermination(3600, TimeUnit.SECONDS)) {//20S
                System.out.println(" 到达指定时间，还有线程没执行完，不再等待，关闭线程池!");
                exec3.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

     */

        long endTime = System.currentTimeMillis();
        long usedTime = (endTime - startTime) / 1000;
        System.out.println("Single usedTime:" + usedTime + "s");
        System.out.println("Single RPS:" + testNum * multiSendNum / usedTime);

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
