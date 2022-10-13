package com.okexchain.msg.wasm;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgUpdateAdmin extends MsgBase {

    public MsgUpdateAdmin(){
        setMsgType("wasm/MsgUpdateAdmin");
    }

    public Message produceMsg(String newAdmin){
        MsgUpdateAdminValue value= new MsgUpdateAdminValue();
        value.setNewAdmin(newAdmin);
        value.setSender(this.address);
        Message<MsgUpdateAdminValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("exchain-65");
        EnvInstance.getEnv().setRestServerUrl("https://exchaintestrpc.okex.org");
        MsgUpdateAdmin msg=new MsgUpdateAdmin();
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        msg.init(key);
        Message message= msg.produceMsg("ex1yw4xvtc43me9scqfr2jr2gzvcxd3a9y4eq7gaukreugw2yd2f8tsfem2z7");
        JSONObject res = msg.submit(message, "0.05", "500000", "");

        try {
            boolean succeed = msg.isTxSucceed(res);
            System.out.println("tx " + (succeed ? "succeed": "failed"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
