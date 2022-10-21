package com.okexchain.msg.feesplit;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Result;
import com.okexchain.utils.HttpUtils;

public class FeesplitQuery {

    //%s=contractAddr
    private static final String FEESPLIT_CONTRACT = "/feesplit/contract/%s";


    //%s=withdrawer addr
    private static final String FEESPLIT_WITHDRAWER = "/feesplit/withdrawer-contracts/%s";


    //query Feesplit Info By ContractAddr
    public static String queryFeesplitInfoByContractAddr(String contractAddr) {
        String basePath = String.format(FEESPLIT_CONTRACT, contractAddr);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }


    //query Contract Addr By Withdrawer Addr
    public static String queryContractAddrByWithdrawerAddr(String withdrawerAddr) {
        String basePath = String.format(FEESPLIT_WITHDRAWER, withdrawerAddr);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }
}
