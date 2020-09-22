package io.cosmos.msg;

import io.cosmos.common.EnvInstance;
import io.cosmos.msg.utils.Message;

public class RunAll {


    public static void main(String[] args) {
        try
        {
            int interval = 10000;


            RunMsgSend();
            Thread.sleep(interval);
            RunMsgDelegate() ;
            Thread.sleep(interval);
            RunMsgRedelegate();
            Thread.sleep(interval);
            RunMsgUnbond() ;
            Thread.sleep(interval);
            RunMsgSetWithdrawAddress();
            Thread.sleep(interval);
            RunMsgWithdrawDelegatorReward() ;
            Thread.sleep(interval);
            RunMsgWithdrawValidatorCommission();

        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    static void RunMsgSend() {

        MsgSend msg = new MsgSend();
        msg.setMsgType("cosmos-sdk/MsgSend");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceSendMsg(
                EnvInstance.getEnv().GetDenom(),
                EnvInstance.getEnv().GetTransferAmount(),
                EnvInstance.getEnv().GetNode1Addr());

        msg.submit(messages, "6", "200000", "cosmos transfer");
    }

    static void RunMsgDelegate() {

        MsgDelegate msg = new MsgDelegate();
        msg.setMsgType("cosmos-sdk/MsgDelegate");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message message = msg.produceDelegateMsg(EnvInstance.getEnv().GetDenom(), "100");

        msg.submit(message, "3", "200000", "Delegate memo");
    }

    static void RunMsgRedelegate() {

        MsgRedelegate msg = new MsgRedelegate();
        msg.setMsgType("cosmos-sdk/MsgBeginRedelegate");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceDelegateMsg(
                EnvInstance.getEnv().GetDenom(), "100");

        msg.submit(messages,
                "3",
                "200000",
                "Delegate memo");
    }

    static void RunMsgUnbond() {

        MsgUnbond msg = new MsgUnbond();
        msg.setMsgType("cosmos-sdk/MsgUndelegate");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceDelegateMsg(
                EnvInstance.getEnv().GetDenom(), "100");

        msg.submit(messages,
                "3",
                "200000",
                "Delegate memo");
    }

    static void RunMsgSetWithdrawAddress() {

        MsgSetWithdrawAddress msg = new MsgSetWithdrawAddress();
        msg.setMsgType("cosmos-sdk/MsgModifyWithdrawAddress");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg();

        msg.submit(messages,
                "6",
                "200000",
                "cosmos set withdrawAddr");
    }

    static void RunMsgWithdrawDelegatorReward() {

        MsgWithdrawDelegatorReward msg = new MsgWithdrawDelegatorReward();
        msg.setMsgType("cosmos-sdk/MsgWithdrawDelegationReward");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg();

        msg.submit(messages,
                "6",
                "200000",
                "cosmos set withdrawAddr");
    }

    static void RunMsgWithdrawValidatorCommission() {

        MsgWithdrawValidatorCommission msg = new MsgWithdrawValidatorCommission();
        msg.setMsgType("cosmos-sdk/MsgWithdrawValidatorCommission");
        msg.initMnemonic(EnvInstance.getEnv().GetNode0Mnmonic());

        Message messages = msg.produceMsg();

        msg.submit(messages,
                "6",
                "200000",
                "cosmos withdraw");
    }
}