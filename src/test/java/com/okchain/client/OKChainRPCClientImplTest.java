package com.okchain.client;

import com.okchain.client.impl.OKChainRPCClientImpl;
import org.junit.Test;

import java.util.Base64;

public class OKChainRPCClientImplTest {

    @Test
    public void sendTransaction() {
        String data = "tAHwYl3uCkI8Ht70ChSxLq0NxypP2pN1DUqKCoXeJPNCshImChT0uZ4HU4lve6xWlR9H8BEW8ZwDABIOCgNva2ISBzIyNTAwMDASagom61rphyED1C8GqfBLEgskTUfp2VjnZfYGiojlJuIBMJvj1XcLWd4SQLhEzVhqEyQE4JwkZojT7zMcHqYdtdqK6roZKvEN8gkLbKPqCTTn+bsmOcb4qr1BNe0/rVyxu14vQ6QTvT2UpQw=";
        byte[] transactionData = Base64.getDecoder().decode(data);
        String url = "http://localhost:26657";
        OKChainRPCClientImpl client = OKChainRPCClientImpl.getOKChainClient(url);
        String res = client.sendTransaction(transactionData);
        System.out.println(res);
    }
}