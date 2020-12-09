package com.okexchain.msg;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgBeginRedelegateValue;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

public class MsgRedelegate extends MsgDelegate {

    public static void main(String[] args) {
        MsgRedelegate msg = new MsgRedelegate();
        msg.setMsgType("cosmos-sdk/MsgBeginRedelegate");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceDelegateMsg(
                EnvInstance.getEnv().GetDenom(),
                "100");

        msg.submit(messages,
                "3",
                "200000",
                "Delegate memo");
    }

    protected Message produceDelegateMsg(
                                            String delegateDenom,
                                            String delegateAmount) {

        MsgRedelegate tmp = new MsgRedelegate();
        tmp.initMnemonic(EnvInstance.getEnv().GetNode1Mnmonic());
        String validatorDstAddress = tmp.operAddress;
        String validatorSrcAddress = this.operAddress;

        MsgBeginRedelegateValue delegateValue = new MsgBeginRedelegateValue();
        delegateValue.setValidatorSrcAddress(validatorSrcAddress);
        delegateValue.setValidatorDstAddress(validatorDstAddress);
        delegateValue.setDelegatorAddress(address);
        Token token = new Token();
        token.setDenom(delegateDenom);
        token.setAmount(Utils.NewDecString(delegateAmount));
        delegateValue.setAmount(token);
        Message<MsgBeginRedelegateValue> messageDelegateMulti = new Message<>();
        messageDelegateMulti.setType(msgType);
        messageDelegateMulti.setValue(delegateValue);
        return messageDelegateMulti;
    }

}
