package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgConfirmTokenPairOwnershipValue;

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