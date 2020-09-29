package io.okexchain.crypto.io.cosmos.crypto.hash;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 * @program: cosmos-java-sdk
 * @description:hmacsha算法,目前支持256/512长度的hmacsha
 * @author: liqiang
 * @create: 2019-03-11 12:01
 **/
public class HMacSha {


    public static byte[] hMacSha256(byte[] data, byte[] key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        return mac.doFinal(data);
    }


    public static byte[] hMacSha512(byte[] data, byte[] key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(signingKey);
        return mac.doFinal(data);
    }
}
