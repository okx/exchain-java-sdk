package com.okexchain.msg.dex;

import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgWithdrawDeposit extends MsgBase {

    public MsgWithdrawDeposit() { setMsgType("okexchain/dex/MsgWithdraw"); }

    public Message produce(String denom, String amountDenom, String product) {

        Token amount = new Token();
        amount.setDenom(denom);
        amount.setAmount(Utils.NewDecString(amountDenom));

        MsgWithdrawDepositValue value = new MsgWithdrawDepositValue();

        value.setDepositor(this.address);
        value.setAmount(amount);
        value.setProduct(product);

        Message<MsgWithdrawDepositValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
