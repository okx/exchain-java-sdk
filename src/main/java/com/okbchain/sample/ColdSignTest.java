package com.okbchain.sample;

import com.okbchain.utils.crypto.PrivateKey;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.token.MsgTokenIssue;
import com.okbchain.msg.token.MsgTokenModify;
import com.okbchain.msg.token.MsgTransferOwnership;
import com.okbchain.msg.token.MsgConfirmOwnership;
import com.okbchain.msg.tx.BroadcastTx;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.tx.UnsignedTx;
import com.okbchain.msg.common.Signature;

public class ColdSignTest {

    public static void main(String[] args) {

        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        // token issue
        MsgTokenIssue msg = new MsgTokenIssue();
        msg.init(key.getPubKey()); // key.getAddress(),
        System.out.println(key.getAddress());
        Message messages = msg.produceTokenIssueMsg(
                "fuming-create",
                "rxb",
                "rxb",
                "rxb",
                "100000000",
                "okbchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                true);

        // token modify
        MsgTokenModify msgModify = new MsgTokenModify();
        msgModify.init(key.getPubKey());
        System.out.println(key.getAddress());

        Message messagesModify = msgModify.produceTokenModifyMsg(
                "modify by charles in 2020-09-29",
                true,
                "okbchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "rxb-486",
                "RXBCHARLES",
                true);

        // transferOwnership
        MsgTransferOwnership transfer = new MsgTransferOwnership();
        Message messagesTransfer = transfer.produceTransferOwnerShipMsg(
                "okbchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "okbchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9",
                "rxb-486"
                );



        // confirm ownership
        // admin16 okbchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9
        MsgConfirmOwnership confirm = new MsgConfirmOwnership();
        Message messagesConfirm = confirm.produceConfirmOwnershipMsg(
                "okbchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9",
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
            UnsignedTx unsignedTx = msg.getUnsignedTx(messagesConfirm, "0.00200000", "200000", "");

            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());



            BroadcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.broadcast(signedTx.toJson(), "http://localhost:26659");

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
