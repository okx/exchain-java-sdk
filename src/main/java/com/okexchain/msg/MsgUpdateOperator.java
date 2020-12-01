package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgUpdateOperatorValue;
import com.okexchain.utils.Utils;

public class MsgUpdateOperator extends MsgBase {
    public MsgUpdateOperator() {
        setMsgType("okexchain/dex/UpdateOperator");
    }

    public static void main(String[] args) {
        MsgUpdateOperator msg = new MsgUpdateOperator();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceUpdateOperatorMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "https://captain.okg/operator.json");

        // okexchaincli tx dex edit-operator --website http://captain.okg.com/operator.json --from captain --fees 0.02okt -b block -y
        msg.submit(messages, Utils.NewDecString("0.01000000"), "200000", "okexchain dex create operator!");
    }

    public Message produceUpdateOperatorMsg(String handlingFeeAddress, String website) {

        MsgUpdateOperatorValue value = new MsgUpdateOperatorValue();
        value.setOwner(this.address);
        value.setHandlingFeeAddress(handlingFeeAddress);
        value.setWebsite(website);

        Message<MsgUpdateOperatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}