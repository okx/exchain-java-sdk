package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgWithdrawValue;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgWithdraw extends MsgBase {

    public MsgWithdraw() {
        setMsgType("okexchain/staking/MsgWithdraw");
    }

    public static void main(String[] args) {
        MsgWithdraw msg = new MsgWithdraw();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        Message messages = msg.produceWithdrawMsg(
                "okt",
                Utils.NewDecString("1.00000000"));

        // okexchaincli tx send okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9 okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9 6okt --from captain -y -b block --fees 0.01okt
        msg.submit(messages, Utils.NewDecString("0.01000000"), "200000", "okexchain withdraw staking bonus!");
    }

    public Message produceWithdrawMsg(String denom, String amountDenom) {

        Token quantity = new Token();
        quantity.setDenom(denom);
        quantity.setAmount(amountDenom);

        MsgWithdrawValue value = new MsgWithdrawValue();
        value.setQuantity(quantity);
        value.setDelegatorAddress(this.address);

        Message<MsgWithdrawValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
