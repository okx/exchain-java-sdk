package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.env.LocalEnv;
import com.okexchain.utils.crypto.AddressUtil;
import com.okexchain.utils.crypto.Crypto;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.MsgSend;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.common.Signature;
import sun.jvm.hotspot.debugger.Address;

public class ColdSign {

    public static void main(String[] args) throws Exception {

        EnvInstance.setEnv(new LocalEnv("http://localhost:8545"));

        PrivateKey key = new PrivateKey("raw flame junior chunk mule together aerobic water motor crunch sense alien");

        String okaddress = Crypto.generateAddressFromPub(key.getPubKey());

        System.out.println("okaddress " + okaddress);
        System.out.println(key.getPriKey());
        System.out.println(key.getPubKey());
        MsgSend msg = new MsgSend();
//        msg.init(key.getAddress(), key.getPubKey());

        // or init by account number and sequence number
        msg.init(key.getPubKey(), "0", "10");

        Message messages = msg.produceSendMsg(
                "okt",
                "6.00000000",
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

//            BoardcastTx signedTx = unsignedTx.signed(signature);
            BoardcastTx signedTx = UnsignedTx.genBroadcastTx(unsignedTx.toString(), signature);
            System.out.println(signedTx.toJson());


            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
