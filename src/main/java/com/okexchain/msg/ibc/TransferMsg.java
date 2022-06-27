package com.okexchain.msg.ibc;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.TimeoutHeight;
import com.okexchain.msg.common.Token;

public class TransferMsg extends MsgBase {


    public TransferMsg() {
        setMsgType("cosmos-sdk/MsgTransfer");
    }

    /**
     * ibc transfer msg
     * @param sourceChannel source channel
     * @param amount amount uint is wei
     * @param receiver address of receiver
     * @param timeoutHeight timeout Height
     * @return Message
     */
    public Message produceMsg(String sourceChannel, String amount,String receiver, TimeoutHeight timeoutHeight) {

        TransferMsgValue transferMsgValue = new TransferMsgValue();
        transferMsgValue.setSourceChannel(sourceChannel);
        transferMsgValue.setSourcePort("transfer");
        transferMsgValue.setSender(this.address);
        transferMsgValue.setReceiver(receiver);
        transferMsgValue.setTimeoutHeight(timeoutHeight);
        transferMsgValue.setToken(new Token(amount,"wei"));

        Message<TransferMsgValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(transferMsgValue);

        return msg;
    }
}
