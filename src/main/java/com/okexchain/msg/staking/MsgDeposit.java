package com.okexchain.msg.staking;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.DecCoin;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgDeposit extends MsgBase {
    public MsgDeposit() {
        setMsgType("okexchain/staking/MsgDeposit");
    }


    //https://www.oklink.com/zh-cn/okc-test/tx/E1DA3DC51C2C32BEE56C3F240A79FA404125207EC11CF296E3E130AA33CE5241
    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        MsgDeposit msg = new MsgDeposit();
        msg.init(new PrivateKey(""));
        Message messages = msg.produceMsg("okt", "10.00000000", "ex1l5jugfjaqys4k64rpqud3lymf8a3csg6ds2j4h");
        JSONObject res =  msg.submit(messages, "0.09", "100000000", "");
        System.out.println(res);
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Message produceMsg(String denom, String amountDenom, String delegrator) {
        DecCoin decCoin = new DecCoin();
        decCoin.setDenom(denom);
        decCoin.setAmount(Utils.NewDecString(amountDenom));
        MsgDepositValue value = new MsgDepositValue();
        value.setAmount(decCoin);
        value.setDelegatorAddress(delegrator);
        Message<MsgDepositValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
