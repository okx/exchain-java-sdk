package io.cosmos.common;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class NonHardenedChild {

    private static byte[] HMacSha512(byte[] data, byte[] key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(signingKey);
        return mac.doFinal(data);
    }

    public static byte[] NHchild(byte[] path, byte[] xprv, byte[] xpub) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        // begin build data
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write('N');
        out.write(xpub, 0, xpub.length / 2);
        out.write(path, 0, path.length);
        byte[] data = out.toByteArray();
        // end build data

        // begin build key
        byte[] key = new byte[xpub.length / 2];
        System.arraycopy(xpub, xpub.length / 2, key, 0, xpub.length / 2);
        // end build key

        // doFinal()
        byte[] res = HMacSha512(data, key);

        //begin operate res[:32]
        byte[] f = new byte[res.length / 2];
        System.arraycopy(res, 0, f, 0, res.length / 2);
        f = pruneIntermediateScalar(f);
        System.arraycopy(f, 0, res, 0, res.length / 2);
        //end operate res[:32]

        //begin operate res[:32] again
        int carry = 0;
        int sum = 0;
        for (int i = 0; i < 32; i++) {
            int xprvInt = xprv[i] & 0xFF;
            int resInt = res[i] & 0xFF;
            sum = xprvInt + resInt + carry;
            res[i] = (byte) sum;
            carry = sum >> 8;
        }
        if ((sum >> 8) != 0) {
            System.err.println("sum does not fit in 256-bit int");
        }
        //end operate res[:32] again
        return res;
    }

    private static byte[] pruneIntermediateScalar(byte[] f) {
        f[0] &= 248; // clear bottom 3 bits
        f[29] &= 1; // clear 7 high bits
        f[30] = 0;  // clear 8 bits
        f[31] = 0;  // clear 8 bits
        return f;
    }

    public static byte[] child(byte[] xprv, String[] hpaths) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        byte[][] paths = new byte[][]{
                Hex.decode(hpaths[0]),
                Hex.decode(hpaths[1])
        };
        byte[] res = xprv;
        for (int i = 0; i < hpaths.length; i++) {
            byte[] xpub = DeriveXpub.deriveXpub(res);
            res = NonHardenedChild.NHchild(paths[i], res, xpub);
        }
        return res;
    }
}


