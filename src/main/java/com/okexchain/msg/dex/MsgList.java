package com.okexchain.msg.dex;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.Utils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

public class MsgList extends MsgBase {

    public MsgList() {
        setMsgType("okexchain/dex/MsgList");
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchain-66");
        EnvInstance.getEnv().setRestServerUrl("https://okexbeta.bafang.com");

        MsgList msg = new MsgList();
//        msg.initMnemonic("giggle sibling fun arrow elevator spoon blood grocery laugh tortoise culture tool");
        msg.init("okexchain1ntvyep3suq5z7789g7d5dejwzameu08m6gh7yl", "");
        Message messages = msg.produceListMsg(
                "usdk-017",
                "okt",
                "1.00000000");


        UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.05000000", "500000", "");
//        System.out.println(unsignedTx.toString());
        // okexchaincli tx dex list --from captain --base-asset eos-a99 --quote-asset okt -y -b block --fees 0.01okt
//        msg.submit(messages, "0.05000000", "500000", "okexchain dex list!");
        JSONObject jsonpObject = JSON.parseObject(unsignedTx.toString(), Feature.OrderedField);
        jsonpObject.put("addressIndex", 1);

        System.out.println(jsonpObject.toString());
    }

    public Message produceListMsg(String listAsset, String quoteAsset, String initPrice) {

        MsgListValue value = new MsgListValue();
        value.setOwner(this.address);
        value.setListAsset(listAsset);
        value.setQuoteAsset(quoteAsset);
        value.setInitPrice(Utils.NewDecString(initPrice));

        Message<MsgListValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
