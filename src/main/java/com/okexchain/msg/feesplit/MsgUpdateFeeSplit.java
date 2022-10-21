package com.okexchain.msg.feesplit;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgUpdateFeeSplit extends MsgBase {

    public MsgUpdateFeeSplit() {
        setMsgType("okexchain/MsgUpdateFeeSplit");
    }

    public Message produceMsg(String contractAddress, String deployerAddress, String withdrawerAddress) {
        MsgUpdateFeeSplitValue value = new MsgUpdateFeeSplitValue();
        value.setContractAddress(contractAddress);
        value.setDeployerAddress(deployerAddress);
        value.setWithdrawerAddress(withdrawerAddress);

        Message<MsgUpdateFeeSplitValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        MsgUpdateFeeSplit msg = new MsgUpdateFeeSplit();
        PrivateKey key = new PrivateKey("");
        msg.init(key);

        Message message = msg.produceMsg(
                "0x0554c61F21936dAD6b1F5bDc685e266beBd04234",
                "0xfD25C4265D01215B6aA30838D8fc9b49fb1c411A",
                "0x364509813f99598e4aFdEFdC367cbae588028148"
        );

        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        System.out.println(res);
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
