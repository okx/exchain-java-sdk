package sample;

import io.cosmos.crypto.PrivateKey;
import io.cosmos.msg.MsgBase;
import io.cosmos.msg.MsgUpdateOperator;
import io.cosmos.msg.utils.BoardcastTx;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.UnsignedTx;
import io.cosmos.types.Signature;

public class DexUpdateOperator {
    public static void main(String[] args) {

        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgUpdateOperator msg = new MsgUpdateOperator();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceUpdateOperatorMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "https://captain.okg/operator.json");

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "okexchain dex create operator!");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), "http://localhost:26659");

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}