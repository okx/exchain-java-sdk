package com.okexchain.sample;

import com.okexchain.utils.crypto.AddressUtil;
import com.okexchain.utils.crypto.Crypto;
import com.okexchain.utils.crypto.PrivateKey;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.*;
import java.math.BigInteger;
import java.security.Key;
import java.security.SignatureException;

public class EthAddress {

    public static void main(String[] args) throws Exception {
        TestcreateNewAddressSecp256k1();
    }

    public static void TestcreateNewAddressSecp256k1() throws Exception {
        PrivateKey key = new PrivateKey( "4412948D29D5433F4A6F07DE88BFB6411DB87B7F40AFF81E29F1EE12A2E8210D");
        String priKey = key.getPriKey();

        String addr = AddressUtil.createNewAddressSecp256k1("okexchain", Hex.decode(key.getPubKey()));
        System.out.println("address is : " + addr);
    }

}
