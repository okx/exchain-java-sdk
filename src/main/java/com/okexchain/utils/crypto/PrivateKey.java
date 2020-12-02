package com.okexchain.utils.crypto;

import org.bouncycastle.util.encoders.Hex;

public class PrivateKey {

    protected String pubKeyString;
    protected String address;
    protected String priKeyString;

    public PrivateKey(String mnemonic) {
        if (mnemonic.indexOf(' ') >= 0) {
            priKeyString = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
        } else {
            priKeyString = mnemonic;
        }
        pubKeyString = Hex.toHexString(Crypto.generatePubKeyFromPriv(priKeyString));
        address = Crypto.generateAddressFromPriv(priKeyString);
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
