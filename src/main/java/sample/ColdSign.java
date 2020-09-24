package sample;

import io.cosmos.common.EnvInstance;
import io.cosmos.common.LocalEnv;
import io.cosmos.crypto.PrivateKey;
import io.cosmos.msg.MsgBase;
import io.cosmos.msg.MsgSend;
import io.cosmos.msg.utils.BoardcastTx;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.UnsignedTx;
import io.cosmos.types.Signature;

public class ColdSign {

    public static void main(String[] args) {

        EnvInstance.setEnv(new LocalEnv("http://localhost:26659"));

        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgSend msg = new MsgSend();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceSendMsg(
                "okt",
                "6.00000000",
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain transfer!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
