package crypto;

import com.okexchain.env.EnvInstance;
import com.okexchain.utils.crypto.AddressConvertUtil;
import com.okexchain.utils.crypto.AddressUtil;
import com.okexchain.utils.crypto.Crypto;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoTest {
    @Test
    public void testGeneratePrivateKey() {
        String priv = Crypto.generatePrivateKey();
        System.out.println(priv);
    }

    @Test
    public void testGenerateAddress() {
        String priv = Crypto.generatePrivateKey();
        priv = "2c999c5afe7f0c902846e1b286fed29c5c5914998655d469568560955abe0d5d";
        long startTime = System.currentTimeMillis();
        byte[] pub = Crypto.generatePubKeyFromPriv(priv);
        System.out.println("pubkey");
        System.out.println(Hex.toHexString(pub));
        try {
            String addr = AddressUtil.createNewAddressSecp256k1(EnvInstance.getEnv().GetMainPrefix(), pub);
            System.out.println(addr);
        }catch (Exception e){
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        float excTime = (float) (endTime - startTime) / 1000;
        System.out.println("执行时间：" + excTime + "s");

    }

    @Test
    public void testVerify() {
        String priv = Crypto.generatePrivateKey();
        byte[] msg = new String("hello").getBytes();
        try {
            byte[] sig = Crypto.sign(msg, priv);
            String sigBase64 = Base64.getEncoder().encodeToString(sig);
            byte[] pub = Crypto.generatePubKeyFromPriv(priv);
            boolean res = Crypto.validateSig(msg, Base64.getEncoder().encodeToString(pub), sigBase64);
            Assert.assertEquals(true, res);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generateMnemonic() {
        System.out.println(Crypto.generateMnemonic());
    }

    @Test
    public void generatePrivateKeyFromMnemonic() {
        String mnemonic =
                "depart neither they audit pen exile " +
                        "fire smart tongue express blanket burden " +
                        "culture shove curve address together pottery " +
                        "suggest lady sell clap seek whisper";

//        String mnemonic = "arrive permit dust filter impulse route wing point bubble cement pact bulb";
//        mnemonic = "sentence deputy little switch fiction balcony hollow iron net index sound hollow scare attitude only cushion best candy wonder phone napkin sketch announce derive";
        String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic);

        System.out.println("mnemonic");
        System.out.println(mnemonic);

        System.out.println("prikey");
        System.out.println(prikey);

        System.out.println("pubkey");
        System.out.println(Hex.toHexString(Crypto.generatePubKeyFromPriv(prikey)));

        System.out.println("address");
        System.out.println(Crypto.generateAddressFromPriv(prikey));
    }

    @Test
    public void prefixTest() {
        //"eth_address": "0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"
        //"privateKey": "2843de7dec93946f1022ec9355678fdec3dc49d3140d2314b452a3a4afe78191"
        Assert.assertEquals(AddressConvertUtil.convertFromBech32ToHex("okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56"),"0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a");
        Assert.assertEquals(AddressConvertUtil.convertFromBech32ToHex("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk"),"0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a");
        Assert.assertEquals(AddressConvertUtil.convertFromHexToOkexchainBech32("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"),"okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56");
        Assert.assertEquals(AddressConvertUtil.convertFromHexToExBech32("0x64fAB0187AF0BCfF8499079161d8a0D68Ee8827a"),"ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk");
        Assert.assertEquals(AddressConvertUtil.convertFromExBech32ToOkexchainBech32("ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk"),"okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56");
        Assert.assertEquals(AddressConvertUtil.convertFromOkexchainBech32ToExBech32("okexchain1vnatqxr67z70lpyeq7gkrk9q668w3qn6sufu56"),"ex1vnatqxr67z70lpyeq7gkrk9q668w3qn6hhzuhk");

    }

}
