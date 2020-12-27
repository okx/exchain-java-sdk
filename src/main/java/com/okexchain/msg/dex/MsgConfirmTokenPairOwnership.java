package com.okexchain.msg.dex;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgConfirmTokenPairOwnership extends MsgBase {

    public MsgConfirmTokenPairOwnership () {
        setMsgType("okexchain/dex/MsgConfirmOwnership");
    }

    public Message produceConfirmTokenPairOwnershipMsg (String fromAddress, String product) {

        MsgConfirmTokenPairOwnershipValue value = new MsgConfirmTokenPairOwnershipValue();
        value.setFromAddress(fromAddress);
        value.setProduct(product);

        Message<MsgConfirmTokenPairOwnershipValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;

    }
}