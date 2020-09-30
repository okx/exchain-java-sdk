package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgTransferTradingPairOwnershipValue;
import com.okexchain.msg.common.Signature;
import com.okexchain.utils.Utils;

public class MsgTransferTradingPairOwnership extends MsgBase{
    public MsgTransferTradingPairOwnership() {
        setMsgType("okexchain/dex/MsgTransferTradingPairOwnership");
    }

    public Message produceMsg (String toAddress, String product) {
        Signature defaultToSignature = new Signature();
        defaultToSignature.setPubkey(null);
        defaultToSignature.setSignature(null);

        MsgTransferTradingPairOwnershipValue value  = new MsgTransferTradingPairOwnershipValue();
        value.setFromAddress(this.address);
        value.setToAddress(toAddress);
        value.setProduct(product);
        value.setToSignature(defaultToSignature);

        Message<MsgTransferTradingPairOwnershipValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public String toJson(Message messages){
        String msgJson = Utils.serializer.toJson(messages);
        return msgJson;
    }

    public Message setToSignature(Message messages, Signature toSignature){
        MsgTransferTradingPairOwnershipValue value = (MsgTransferTradingPairOwnershipValue)messages.getValue();
        value.setToSignature(toSignature);
        messages.setValue(value);
        return messages;
    }
}