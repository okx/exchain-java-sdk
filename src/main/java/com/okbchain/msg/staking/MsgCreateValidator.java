package com.okbchain.msg.staking;

import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.msg.common.Description;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.Utils;


public class MsgCreateValidator extends MsgBase {

    public MsgCreateValidator() {
        setMsgType("okbchain/staking/MsgCreateValidator");
    }

    public static void main(String[] args) {
        EnvInstance.setEnv("ok");
        MsgCreateValidator msg = new MsgCreateValidator();
        msg.setMsgType("cosmos-sdk/MsgCreateValidator");
        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");
        Message messages = msg.produceMsg();
        msg.submit(messages, "6", "200000", "");
    }

    public Message produceMsg() {
        MsgCreateValidatorValue value = new MsgCreateValidatorValue();

        value.setDelegatorAddress(this.address);
        value.setValidatorAddress(this.operAddress);
        value.setPubKey("okchainvalconspub1zcjduepqwfr8lelpqerf8xyc63vqtje0wvhd68h7uce6ludygc28uj5hc9ushev2kp");

        Description d = new Description();
        d.setDetails("1");
        d.setIdentity("2");
        d.setMoniker("3");
        d.setWebsite("4");


        Token t = new Token();
        t.setAmount(Utils.NewDecString("10000.00000000"));
        t.setDenom(EnvInstance.getEnv().GetDenom());

        value.setDescription(d);
        value.setMinSelfDelegation(t);


        Message<MsgCreateValidatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public Message produceMsg(String nodePubKey, String moniker, String website, String identity, String details, String msdAmount) {
        MsgCreateValidatorValue value = new MsgCreateValidatorValue();

        value.setDelegatorAddress(this.address);
        value.setValidatorAddress(this.operAddress);
        value.setPubKey(nodePubKey);

        Description d = new Description();
        d.setMoniker(moniker);
        d.setWebsite(website);
        d.setIdentity(identity);
        d.setDetails(details);
        value.setDescription(d);

        Token t = new Token();
        t.setAmount(Utils.NewDecString(msdAmount));
        t.setDenom(EnvInstance.getEnv().GetDenom());
        value.setMinSelfDelegation(t);

        Message<MsgCreateValidatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
