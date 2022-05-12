package com.okexchain.msg.ibc;

import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Result;
import com.okexchain.msg.ibc.tx.TxEvent;
import com.okexchain.utils.HttpUtils;

/***
 * @author lixuqing
 * @date 2022-05-12
 * @TxQuery
 */
public class TxQuery {
    /***
     * query for a transaction on a given network by transaction hash and chain ID.
     * @param hash
     * @return
     */
    public static JSONObject queryTx(String hash){
        StringBuilder queryUrl=new StringBuilder(EnvInstance.getEnv().GetRestServerUrl()+EnvInstance.getEnv().GetRestPathPrefix());
        queryUrl.append("/txs/");
        queryUrl.append(hash);
        Result res = HttpUtils.httpGetResult(queryUrl.toString());
        if (res.isSuccess()) {
            return JSONObject.parseObject(res.getData());
        }
        return null;
    }

    /***
     * query for transactions on a given network by chain ID and a set of transaction event.
     * @return
     */
    public static JSONObject queryTxs(TxEvent txEvent){
        String baseStr="message.action=%s&message.sender=%s&page=%s&limit=%s&tx.minheight=%s&tx.maxheight=%s";
        String basePath=String.format(baseStr,txEvent.getAction(),txEvent.getSender(),txEvent.getPage(),txEvent.getLimit(),txEvent.getMinHeight(),txEvent.getMaxHeight());
        StringBuilder queryUrl=new StringBuilder(EnvInstance.getEnv().GetRestServerUrl()+EnvInstance.getEnv().GetRestPathPrefix());
        queryUrl.append("/txs?");
        queryUrl.append(basePath);
        Result res = HttpUtils.httpGetResult(queryUrl.toString());
        if (res.isSuccess()) {
            return JSONObject.parseObject(res.getData());
        }
        return null;
    }
}
