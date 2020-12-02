package com.okexchain.utils.crypto;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import java.math.BigInteger;

public class PrivateKey {

    protected String pubKeyString;
    protected String address;
    protected String priKeyString;

    public PrivateKey(String mnemonic) {
        if (mnemonic.indexOf(' ') >= 0) {
            // TODO
            priKeyString = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
        } else {
            priKeyString = mnemonic;
        }
        BigInteger pubKey = Sign.publicKeyFromPrivate(new BigInteger(priKeyString, 16));
        pubKeyString = Numeric.toHexStringWithPrefixZeroPadded(pubKey, 64 << 1);
        address = Crypto.generateAddressFromPub(pubKeyString);
    }

    public PrivateKey(String priKey,String pubKey,String addr) {
        this.priKeyString = priKey;
        this.pubKeyString = pubKey;
        this.address = addr;
    }

    public String getAddress() {
        return address;
    }

    public String getPubKey() {
        return pubKeyString;
    }

    public String getPriKey() {
        return priKeyString;
    }
}
