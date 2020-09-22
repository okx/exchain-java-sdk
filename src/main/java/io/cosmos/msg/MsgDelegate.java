package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgDelegateValue;
import io.cosmos.types.Token;


public class MsgDelegate extends MsgBase {

    public static void main(String[] args) {
        MsgDelegate msg = new MsgDelegate();
        msg.setMsgType("cosmos-sdk/MsgDelegate");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message message = msg.produceDelegateMsg(
                EnvInstance.getEnv().GetDenom(), "100");

        msg.submit(message,
                "3",
                "200000",
                "Delegate memo");
    }


    protected Message produceDelegateMsg(String delegateDenom, String delegateAmount) {

        String validatorAddress = this.operAddress;
        MsgDelegateValue delegateValue = new MsgDelegateValue();
        delegateValue.setValidatorAddress(validatorAddress);
        delegateValue.setDelegatorAddress(address);
        //amount
        Token token = new Token();
        token.setDenom(delegateDenom);
        token.setAmount(delegateAmount);
        delegateValue.setAmount(token);
        Message<MsgDelegateValue> messageDelegateMulti = new Message<>();
        messageDelegateMulti.setType(msgType);
        messageDelegateMulti.setValue(delegateValue);
        return messageDelegateMulti;
    }

}
