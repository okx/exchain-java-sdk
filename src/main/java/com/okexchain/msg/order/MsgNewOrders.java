package com.okexchain.msg.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvBase;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;
import com.sun.tools.doclint.Env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MsgNewOrders extends MsgBase {

    public MsgNewOrders() {
        setMsgType("okexchain/order/MsgNew");
    }

    public Message produceMsg(List<OrderItem> orderItems) {
        MsgNewOrdersValue value = new MsgNewOrdersValue();
        value.setSender(this.address);
        value.setNewOrderItem(orderItems);

        Message<MsgNewOrdersValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public String[] getOrderIDs(JSONObject result) throws Exception {
        String orders = getMatchedAttribute(result, "orders");
        JSONArray orderJSONs = JSONObject.parseArray(orders);
        String[] orderIDs = new String[orderJSONs.size()];
        for (int i = 0; i < orderJSONs.size(); i++) {
            orderIDs[i] = orderJSONs.getJSONObject(i).getString("orderid");
        }
        return orderIDs;
    }

    public static void main(String[] args) throws Exception {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgNewOrders msg = new MsgNewOrders();
        msg.init(key);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = new OrderItem("2", "usdk-5f7_okt", "2", "SELL");
        orderItems.add(item);

        Message messages = msg.produceMsg(orderItems);

        JSONObject res = msg.submit(messages, "0.02000000", "200000", "");

        String[] orderIDs = msg.getOrderIDs(res);
        System.out.println(Arrays.toString(orderIDs));
    }
}
