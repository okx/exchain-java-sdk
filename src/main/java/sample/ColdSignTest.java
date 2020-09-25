package sample;

import io.cosmos.crypto.PrivateKey;
import io.cosmos.msg.MsgBase;
import io.cosmos.msg.MsgSend;
import io.cosmos.msg.MsgTokenIssue;
import io.cosmos.msg.utils.BoardcastTx;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.UnsignedTx;
import io.cosmos.types.Signature;

public class ColdSignTest {

    public static void main(String[] args) {

        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgTokenIssue msg = new MsgTokenIssue();
        msg.init(key.getAddress(), key.getPubKey());
        System.out.println(key.getAddress());
        Message messages = msg.produceToeknIssueMsg(
                "有钱任性",
                "rxb",
                "rxb",
                "rxb",
                "80000000000",
                key.getAddress(),
                true);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.00200000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());



            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), "http://localhost:26659");

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
