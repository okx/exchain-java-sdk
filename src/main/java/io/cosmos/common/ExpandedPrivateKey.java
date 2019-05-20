package io.cosmos.common;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class ExpandedPrivateKey {
    public static byte[] HMacSha512(byte[] data, byte[] key)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(signingKey);
        return mac.doFinal(data);
    }

    public static byte[] ExpandedPrivateKey(byte[] data)
            throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        // "457870616e64" is "Expand" hex.
        byte[] res = HMacSha512(data, Hex.decode("457870616e64"));
        for (int i = 0; i <= 31; i++) {
            res[i] = data[i];
        }
        return res;
    }
}


