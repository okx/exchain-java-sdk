package io.cosmos.common;

import com.google.crypto.tink.subtle.Ed25519;

public class DeriveXpub {
    public static byte[] deriveXpub(byte[] xprv) {
        byte[] xpub = new byte[xprv.length/2];
//        byte[] scalar = new byte[xprv.length / 2];
////        for (int i = 0; i < xprv.length / 2; i++) {
////            scalar[i] = xprv[i];
////        }
//        System.arraycopy(xprv, 0, scalar, 0, xprv.length / 2);
//        byte[] buf = Ed25519.scalarMultWithBaseToBytes(scalar);
////        for (int i = 0; i < buf.length; i++) {
////            xpub[i] = buf[i];
////        }
//        System.arraycopy(buf, 0, xpub, 0, buf.length);
////        for (int i = xprv.length / 2; i < xprv.length; i++) {
////            xpub[i] = xprv[i];
////        }
        System.arraycopy(xprv, xprv.length / 2, xpub, 0, xprv.length / 2);
        return xpub;
    }
}
