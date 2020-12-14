package com.okexchain.sample;

import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.utils.crypto.PrivateKey;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.MsgSend;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.msg.common.Signature;;

public class ColdSign {

    public static void main(String[] args) {
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
//        PrivateKey key = new PrivateKey("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        MsgSend msg = new MsgSend();
        msg.init(key.getAddress(), key.getPubKey());
//        msg.init(key.getAddress(), "0", "1", key.getPubKey());

        // or init by account number and sequence number
//        msg.init(key.getPubKey(), "0", "10");

        Message messages = msg.produceSendMsg(
                EnvInstance.getEnv().GetDenom(),
                "6.00000000",
                "okexchain1q6ls3h64gkxq0r73u2eqwwr7d5mp583f2ats92");

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
