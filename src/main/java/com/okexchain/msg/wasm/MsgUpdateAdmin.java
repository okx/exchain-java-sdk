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

    public Message produceMsg(String newAdmin,String contract){
        MsgUpdateAdminValue value= new MsgUpdateAdminValue();
        value.setNewAdmin(newAdmin);
        value.setContract(contract);
        value.setSender(this.address);
        Message<MsgUpdateAdminValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnvLocalNet();
        MsgUpdateAdmin msg=new MsgUpdateAdmin();
        PrivateKey key = new PrivateKey("8FF3CA2D9985C3A52B459E2F6E7822B23E1AF845961E22128D5F372FB9AA5F17");
        msg.init(key);
        Message message= msg.produceMsg("ex1h0j8x0v9hs4eq6ppgamemfyu4vuvp2sl0q9p3v","ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf");
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
