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
        try {
            address = AddressUtil.createNewAddressSecp256k1("okexchain", Hex.decode(pubKeyString));
        } catch (Exception e) {
            System.out.println("okexchain derive failed");
        }
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
