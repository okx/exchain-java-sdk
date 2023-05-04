package com.okbchain.msg.ibc;

import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.TimeoutHeight;
import com.okbchain.msg.common.Token;

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
