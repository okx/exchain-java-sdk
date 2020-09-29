package sample;

import io.cosmos.crypto.PrivateKey;
import io.cosmos.msg.MsgBase;
import io.cosmos.msg.MsgSend;
import io.cosmos.msg.MsgTokenIssue;
import io.cosmos.msg.MsgTokenModify;
import io.cosmos.msg.MsgTransferOwnership;
import io.cosmos.msg.MsgConfirmOwnership;
import io.cosmos.msg.utils.BoardcastTx;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.UnsignedTx;
import io.cosmos.types.Signature;

public class ColdSignTest {

    public static void main(String[] args) {

//        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");
        PrivateKey key = new PrivateKey("a786d7b5123359eb33dff6909b86a8aaf65927eaa4484c5d1d45a51c35242500");

        // token issue
        MsgTokenIssue msg = new MsgTokenIssue();
        msg.init(key.getAddress(), key.getPubKey()); // key.getAddress(),
        System.out.println(key.getAddress());
        Message messages = msg.produceTokenIssueMsg(
                "fuming-create",
                "rxb",
                "rxb",
                "rxb",
                "100000000",
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                true);

        // token modify
        MsgTokenModify msgModify = new MsgTokenModify();
        msgModify.init(key.getAddress(), key.getPubKey());
        System.out.println(key.getAddress());

        Message messagesModify = msgModify.produceTokenModifyMsg(
                "modify by charles in 2020-09-29",
                true,
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "rxb-486",
                "RXBCHARLES",
                true);

        // transferOwnership
        MsgTransferOwnership transfer = new MsgTransferOwnership();
        Message messagesTransfer = transfer.produceTransferOwnerShipMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9",
                "rxb-486"
                );



        // confirm ownership
        // admin16 okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9
        MsgConfirmOwnership confirm = new MsgConfirmOwnership();
        Message messagesConfirm = confirm.produceConfirmOwnershipMsg(
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9",
                "rxb-486"
        );



        try {
            // token issue
//            UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.00200000", "200000", "");

            // token modify
//            UnsignedTx unsignedTx = msg.getUnsignedTx(messagesModify,"0.00200000", "200000", "");

            // transferOwnership
//            UnsignedTx unsignedTx = msg.getUnsignedTx(messagesTransfer,"0.00200000", "200000", "");

            // confirm ownership
            UnsignedTx unsignedTx = msg.getUnsignedTx(messagesConfirm,"0.00200000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());



            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), "http://localhost:26659");

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }





}
