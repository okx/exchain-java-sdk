package com.okbchain.msg.ibc;

import com.alibaba.fastjson.JSONObject;
import com.okbchain.env.EnvBase;
import com.okbchain.env.EnvInstance;
import com.okbchain.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HeaderQuery {

    public static JSONObject queryHeader(String height){
        String data="{\"jsonrpc\":\"2.0\",\"id\":0,\"method\":\"commit\",\"params\":{\"height\":\""+height+"\"}}";
        String result="";
        try {
            result=HttpUtils.sendPostDataByJson(EnvInstance.getEnv().GetRestServerUrl(),data,"");
            System.out.println(EnvInstance.getEnv().GetRestServerUrl());
        }catch (Exception e){
            log.error("Error:{}",e.toString());
        }
        return JSONObject.parseObject(result);
    }

    public static void main(String []args){
        EnvBase env = EnvInstance.getEnv();
        env.setRestServerUrl("http://127.0.0.1:36657");
        JSONObject json= HeaderQuery.queryHeader("100");
        System.out.println(json);
    }
}
