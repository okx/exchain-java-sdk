package com.okexchain.msg.dex;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

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