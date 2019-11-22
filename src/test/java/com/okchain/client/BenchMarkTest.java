package com.okchain.client;

import com.okchain.client.impl.OKChainRPCClientImpl;
import com.okchain.types.BaseModel;
import org.junit.Assert;
import org.junit.Test;

public class BenchMarkTest {
    private static String url_rpc = "https://okexbeta.bafang.com/okchain/v1/rpc";
    //private static String url_rpc = "http://3.13.150.20:26657";
    @Test
    public void testGetToken() {
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(this.url_rpc);
        BaseModel bm;
        bm = client.getToken("okb");
        for (int i=0;i<100;i++){
            System.out.println(i);
            bm = client.queryBlock(0);
        }

        System.out.println(bm);
        Assert.assertEquals(bm.getCode(), "0");
    }
}
