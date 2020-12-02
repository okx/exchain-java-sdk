package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.env.LocalEnv;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.AddressUtil;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.MsgSend;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.common.Signature;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import java.math.BigInteger;

public class ColdSign {

    public static void main(String[] args) {
//        BigInteger privKey = new BigInteger("8145BFB1D3ACC216C54490952C994D5E3BCE09DD65AE73D0C79F892284F721E7", 16);
//        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
//        String addressETH = Keys.getAddress(pubKey);
//        String addressOK = AddressUtil.createNewAddressETHSecp256k1(EnvInstance.getEnv().GetMainPrefix(),Hex.decode(addressETH));

        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));

        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
//        PrivateKey key = new PrivateKey("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
//        PrivateKey key = new PrivateKey(privKey.toString(16),
//                Numeric.toHexStringWithPrefixZeroPadded(pubKey, 64 << 1),
//                addressOK);

        MsgSend msg = new MsgSend();
        msg.init(key.getAddress(), key.getPubKey());

        // or init by account number and sequence number
//        msg.init(key.getPubKey(), "0", "10");

        Message messages = msg.produceSendMsg(
                "tokt",
                "6.00000000",
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = UnsignedTx.genBroadcastTx(unsignedTx.toString(), signature);
            System.out.println(signedTx.toJson());


            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
