package com.okexchain.client;

import com.okexchain.client.impl.OKEXChainRPCClientImpl;
import com.okexchain.types.BaseModel;
import org.junit.Assert;
import org.junit.Test;

public class BenchMarkTest {
    private static String URL_RPC = "http://127.0.0.1:26657";

    @Test
    public void getTokenTest() {
        OKEXChainRPCClientImpl client = OKEXChainRPCClientImpl.getOKEXChainClient(URL_RPC);
        BaseModel bm;
        bm = client.getToken("okb");
        for (int i = 0; i < 100; i++) {
            bm = client.queryBlock(i);
        }
        Assert.assertEquals(bm.getCode(), 0);
    }
}
