package com.okexchain.msg.feesplit;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgRegisterFeeSplit extends MsgBase {

    public MsgRegisterFeeSplit() {
        setMsgType("okexchain/MsgRegisterFeeSplit");
    }

    public Message produceMsg(String contractAddress, String deployerAddress, String withdrawerAddress, int[] nonce) throws Exception {
        MsgRegisterFeeSplitValue value = new MsgRegisterFeeSplitValue();
        value.setContractAddress(contractAddress);
        value.setDeployerAddress(deployerAddress);
        value.setWithdrawerAddress(withdrawerAddress);
        value.setNonce(nonce);
        Message<MsgRegisterFeeSplitValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    //test result:https://www.oklink.com/zh-cn/okc-test/tx/24A418BFF7FAC3CAA8323B1C72B386EE8E06A80A19F32A934E7C3CEF81DD2ED1
    //test result:https://www.oklink.com/zh-cn/okc-test/tx/39C82A5CC19447E1A8590D3DF7DBFA67044A84E5CB35E473015D1D4BA4E7F521
    public static void main(String[] args) throws Exception {
        EnvInstance.getEnvTestNet();
        MsgRegisterFeeSplit msg = new MsgRegisterFeeSplit();
        PrivateKey key = new PrivateKey("8d8322c9a4356247d9d603eff9e163c5b9166a6a7b92926d38aac813df60db5c");
        msg.init(key);
        Message message = msg.produceMsg(
                "0x8836b1c81bA9D4f73D397a67160a5fC800bFcd03",
                "0xfD25C4265D01215B6aA30838D8fc9b49fb1c411A",
                "",
                new int[]{});

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
