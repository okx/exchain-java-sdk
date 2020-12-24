package com.okexchain.sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Signature;
import com.okexchain.msg.order.MsgCancelOrders;
import com.okexchain.msg.order.MsgNewOrders;
import com.okexchain.msg.order.OrderItem;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.crypto.PrivateKey;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public static void main(String[] args) throws JsonProcessingException {
        EnvBase env = EnvInstance.getEnv();
        env.setChainID("okexchainevm-8");
        env.setRestServerUrl("http://localhost:8545");
        testMsgNewOrders();
//        testMsgCancelOrders();
    }

    static void testMsgNewOrders() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgNewOrders msg = new MsgNewOrders();
        msg.init(key.getAddress(), key.getPubKey());

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = new OrderItem("2", "usdk-5f7_okt", "2", "SELL");
        orderItems.add(item);

        Message messages = msg.produceMsg(orderItems);

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            JSONObject result = MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

            String orderID = result.getJSONArray("raw_log").getJSONObject(0).
                    getJSONArray("events").getJSONObject(0).
                    getJSONArray("attributes").getJSONObject(3).
                    getJSONArray("value").getJSONObject(0).getString("orderid");

            System.out.println(orderID);


        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    static void testMsgCancelOrders() {
        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");

        MsgCancelOrders msg = new MsgCancelOrders();
        msg.init(key.getAddress(), key.getPubKey());

        ArrayList<String> orderIDs = new ArrayList<>();
        orderIDs.add("ID0000001373-1");
        String[] ids = new String[orderIDs.size()];

        Message messages = msg.produceMsg(orderIDs.toArray(ids));

        try {
            UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.02000000", "200000", "");
            Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

            BoardcastTx signedTx = unsignedTx.signed(signature);

            MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }
}
