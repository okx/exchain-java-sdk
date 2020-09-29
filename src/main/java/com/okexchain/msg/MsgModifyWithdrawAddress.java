package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgModifyWithdrawAddressValue;

public class MsgModifyWithdrawAddress extends MsgBase {

    public MsgModifyWithdrawAddress() {
        setMsgType("okexchain/distribution/MsgModifyWithdrawAddress");
    }

    public static void main(String[] args) {
        MsgModifyWithdrawAddress msg = new MsgModifyWithdrawAddress();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceModifyWithdrawAddressMsg(
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9");

        msg.submit(messages, "0.01000000", "200000", "okexchain modify withdraw address!");
    }

    public Message produceModifyWithdrawAddressMsg(String withdrawAddress) {

        MsgModifyWithdrawAddressValue value = new MsgModifyWithdrawAddressValue();
        value.setDelegatorAddress(this.address);
        value.setWithdrawAddress(withdrawAddress);

        Message<MsgModifyWithdrawAddressValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
