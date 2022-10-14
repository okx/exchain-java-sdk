package com.okexchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;


public class MsgClearAdmin extends MsgBase {

    public MsgClearAdmin(){
        setMsgType("wasm/MsgClearAdmin");
    }

    public Message produceMsg(String contract){

        MsgClearAdminValue value=new MsgClearAdminValue();
        value.setContract(contract);
        value.setSender(this.address);
        Message<MsgClearAdminValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("exchain-67");
        EnvInstance.getEnv().setRestServerUrl("http://127.0.0.1:8545");
        EnvInstance.getEnv().setRestPathPrefix("/exchain/v1");
        EnvInstance.getEnv().setTxUrlPath("/exchain/v1/txs");

        MsgClearAdmin msg=new MsgClearAdmin();
        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");
        msg.init(key);
        Message message= msg.produceMsg("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf");
        JSONObject res = msg.submit(message, "0.03", "100000000", "");
        System.out.println(res);
        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
