package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgCreateOperatorValue;

public class MsgCreateOperator extends MsgBase {
    public MsgCreateOperator() {
        setMsgType("okexchain/dex/CreateOperator");
    }

    public static void main(String[] args) {
        MsgCreateOperator msg = new MsgCreateOperator();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceCreateOperatorMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "https://captain.okg/operator.json");

        // okexchaincli tx dex register-operator --website http://captain.okg.com/operator.json --from captain --fees 0.02okt -b block -y
        msg.submit(messages, "0.01000000", "200000", "okexchain dex create operator!");
    }

    public Message produceCreateOperatorMsg(String handlingFeeAddress, String website) {

        MsgCreateOperatorValue value = new MsgCreateOperatorValue();
        value.setOwner(this.address);
        value.setHandlingFeeAddress(handlingFeeAddress);
        value.setWebsite(website);

        Message<MsgCreateOperatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}