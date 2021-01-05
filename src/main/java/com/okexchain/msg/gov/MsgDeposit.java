package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MsgDeposit extends MsgBase {

    public MsgDeposit() {
        setMsgType("okexchain/gov/MsgDeposit");
    }

    public static void main(String[] args) throws Exception {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        MsgDeposit msg = new MsgDeposit();

        msg.initMnemonic("giggle sibling fun arrow elevator spoon blood grocery laugh tortoise culture tool");

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom(EnvInstance.getEnv().GetDenom());
        amount.setAmount(Utils.NewDecString("100"));
        amountList.add(amount);

        Message messages = msg.produceDepositMsg(
                "2",
                amountList);

        JSONObject res = msg.submit(messages, "0.05000000", "500000", "okexchain gov vot!");
        boolean succeed = msg.isTxSucceed(res);
        System.out.println("deposit " + (succeed ? "succeed" : "failed"));
    }

    public Message produceDepositMsg(String ProposalID, List<Token> amount) {

        MsgDepositValue value = new MsgDepositValue();
        value.setProposalID(ProposalID);
        value.setDepositor(this.address);
        value.setAmount(amount);

        Message<MsgDepositValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

}
