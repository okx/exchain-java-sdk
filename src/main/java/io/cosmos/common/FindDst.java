package io.cosmos.common;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Hex;

public class FindDst {

    public static Logger logger = Logger.getLogger(FindDst.class);

    public static int find(String[] privateKeys, String xpub) throws Exception {
        // 多签情况下，找到xpub对应的private key的下标 dst
        int dst = -1;
        for (int k = 0; k < privateKeys.length; k++) {
            byte[] tempXpub = DeriveXpub.deriveXpub(Hex.decode(privateKeys[k]));
            if (xpub.equals(Hex.toHexString(tempXpub))) {
                dst = k;
                logger.info("private[dst]: "+privateKeys[dst]);
                break;
            }
        }
        if (dst == -1) {
            throw new Exception("Not a proper private key to sign transaction.");
        }
        return dst;
    }
}
