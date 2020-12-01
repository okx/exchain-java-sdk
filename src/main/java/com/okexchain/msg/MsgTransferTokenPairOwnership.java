package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgTransferTokenPairOwnershipValue;

public class MsgTransferTokenPairOwnership extends MsgBase {

    public MsgTransferTokenPairOwnership () {
        setMsgType("okexchain/dex/MsgTransferTradingPairOwnership");
    }
    public Message produceTransferTokenPairOwnershipMsg (String fromAddress, String toAddress, String product) {

        MsgTransferTokenPairOwnershipValue value = new MsgTransferTokenPairOwnershipValue();
        value.setFromAddress(fromAddress);
        value.setProduct(product);
        value.setToAddress(toAddress);

        Message<MsgTransferTokenPairOwnershipValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;

    }
}