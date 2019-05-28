package io.okchain.crypto;

import io.okchain.crypto.Crypto;
import io.okchain.util.AddressUtil;
import org.junit.Test;

public class CryptoTest {
    @Test
    public void testGeneratePrivateKey() {
        String priv = Crypto.generatePrivateKey();
        System.out.println(priv);
    }

    @Test
    public void testGenerateAddress() {
        String priv = Crypto.generatePrivateKey();
        byte[] pub = Crypto.generatePubkeyFromPriv(priv);
        try {
            String addr = AddressUtil.createNewAddressSecp256k1("okchain",pub);
            System.out.println(addr);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
