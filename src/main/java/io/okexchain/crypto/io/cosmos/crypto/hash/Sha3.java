package io.okexchain.crypto.io.cosmos.crypto.hash;

import org.bouncycastle.crypto.digests.SHA3Digest;

/**
 * @program: cosmos-java-sdk
 * @description:sha3(nist-keccak)算法,目前支持224/256/384/512长度的sha3
 * @author: liqiang
 * @create: 2019-03-11 11:59
 **/
public class Sha3 {

    public static byte[] sha3224(byte[]... args) {
        SHA3Digest digest = new SHA3Digest(224);
        for (byte[] bytes : args) {
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[224 / 8];
        digest.doFinal(out, 0);
        return out;
    }

    public static byte[] sha3256(byte[]... args) {
        SHA3Digest digest = new SHA3Digest(256);
        for (byte[] bytes : args) {
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[256 / 8];
        digest.doFinal(out, 0);
        return out;
    }



    public static byte[] sha3384(byte[]... args) {
        SHA3Digest digest = new SHA3Digest(384);
        for (byte[] bytes : args) {
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[384 / 8];
        digest.doFinal(out, 0);
        return out;
    }

    public static byte[] sha3512(byte[]... args) {
        SHA3Digest digest = new SHA3Digest(512);
        for (byte[] bytes : args) {
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[512 / 8];
        digest.doFinal(out, 0);
        return out;
    }
}
