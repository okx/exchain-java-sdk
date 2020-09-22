package io.cosmos.crypto.hash;

import org.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.RIPEMD256Digest;
import org.bouncycastle.crypto.digests.RIPEMD320Digest;

public class Ripemd {

    public static byte[] ripemd128(byte[]... args) {
        RIPEMD128Digest digest = new RIPEMD128Digest();
        for (byte[] bytes : args) {
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[128 / 8];
        digest.doFinal(out, 0);
        return out;
    }

    public static byte[] ripemd160(byte[]... args) {
        RIPEMD160Digest digest = new RIPEMD160Digest();
        for (byte[] bytes : args) {
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[160 / 8];
        digest.doFinal(out, 0);
        return out;
    }

    public static byte[] ripemd256(byte[]... args) {
        RIPEMD256Digest digest = new RIPEMD256Digest();
        for (int i = 0; i < args.length; i++) {
            byte[] bytes = args[i];
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[256 / 8];
        digest.doFinal(out, 0);
        return out;
    }

    public static byte[] ripemd320(byte[]... args) {
        RIPEMD320Digest digest = new RIPEMD320Digest();
        for (int i = 0; i < args.length; i++) {
            byte[] bytes = args[i];
            digest.update(bytes, 0, bytes.length);
        }
        byte[] out = new byte[320 / 8];
        digest.doFinal(out, 0);
        return out;
    }
}
