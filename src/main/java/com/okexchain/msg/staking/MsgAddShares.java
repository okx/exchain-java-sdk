package com.okexchain.msg.staking;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;

public class MsgAddShares extends MsgBase {

    public MsgAddShares() {
        setMsgType("okexchain/staking/MsgAddShares");
    }

   public static void main(String[] args) {
        MsgAddShares msg = new MsgAddShares();
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

        String [] validators = {"okexchainvaloper10q0rk5qnyag7wfvvt7rtphlw589m7frshchly8"};
        Message messages = msg.produceMsg("okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9", validators);


        msg.submit(messages, "6.00000000", "200000", "");
    }

    public Message produceMsg(String delegator, String [] validators) {
        MsgAddSharesValue value = new MsgAddSharesValue();
        value.setDelAddr(delegator);
        value.setValAddrs(validators);

        Message<MsgAddSharesValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
