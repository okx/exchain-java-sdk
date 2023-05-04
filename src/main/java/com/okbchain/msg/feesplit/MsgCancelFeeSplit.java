package com.okbchain.msg.feesplit;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.MsgBase;
import com.okbchain.msg.common.Message;
import com.okbchain.utils.crypto.PrivateKey;

public class MsgCancelFeeSplit extends MsgBase {

    public MsgCancelFeeSplit() {
        setMsgType("okbchain/MsgCancelFeeSplit");
    }

    public Message produceMsg(String contractAddress, String deployerAddress) {
        MsgCancelFeeSplitValue value = new MsgCancelFeeSplitValue();
        value.setContractAddress(contractAddress);
        value.setDeployerAddress(deployerAddress);
        Message<MsgCancelFeeSplitValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    //test result:https://www.oklink.com/zh-cn/okc-test/tx/8DC26EA2EB513E42E17337B25D46C7E5E043F4D1F785317D32CE66894F8E631A
    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        MsgCancelFeeSplit msg = new MsgCancelFeeSplit();
        PrivateKey key = new PrivateKey("");
        msg.init(key);

        Message message = msg.produceMsg(
                "0x3f16A822FBfBB636EE07D55EC22Fc84E5A88f800",
                "0xfD25C4265D01215B6aA30838D8fc9b49fb1c411A"
        );

        JSONObject res = msg.submit(message, "0.09", "100000000", "");
        System.out.println(res);
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed" : "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
