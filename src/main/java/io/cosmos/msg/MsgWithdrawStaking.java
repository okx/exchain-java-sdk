package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgWithdrawStakingValue;
import io.cosmos.types.DecCoin;

public class MsgWithdrawStaking  extends MsgBase {
    public static void main(String[] args) {
        EnvInstance.setEnv("okq");

        MsgWithdrawStaking msg = new MsgWithdrawStaking();
        msg.setMsgType("okexchain/staking/MsgWithdraw");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg("okt", "10.00000000", "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9");

        msg.submit(messages, "6.00000000", "200000", "");
    }

    public Message produceMsg(String denom, String amountDenom, String delegrator) {

        DecCoin d = new DecCoin();
        d.setDenom(denom);
        d.setAmount(amountDenom);

        MsgWithdrawStakingValue value = new MsgWithdrawStakingValue();

        System.out.println("this.operAddress:");
        System.out.println(this.operAddress);

        value.setAmount(d);
        value.setDelegatorAddress(delegrator);

        System.out.println("println this.operAdddress");

        Message<MsgWithdrawStakingValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
