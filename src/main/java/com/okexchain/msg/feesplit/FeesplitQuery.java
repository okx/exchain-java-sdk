package com.okexchain.msg.feesplit;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.common.Result;
import com.okexchain.utils.HttpUtils;

public class FeesplitQuery {

    //%s=contractAddr
    private static final String FEESPLIT_CONTRACT = "/feesplit/contract/%s";

    //%s=withdrawer addr
    //%s=page
    //%s=limit
    private static final String FEESPLIT_WITHDRAWER = "/feesplit/withdrawer/%s?page=%s&limit=%s";

    //%s=deployer addr
    //%s=page
    //%s=limit
    private static final String FEESPLIT_DEPLOYER="/feesplit/deployer/%s?page=%s&limit=%s";

    //query Feesplit Info By ContractAddr
    public static String queryFeesplitInfoByContractAddr(String contractAddr) {
        String basePath = String.format(FEESPLIT_CONTRACT, contractAddr);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }


    //query Contract Addr By deployer Addr
    public static String queryContractAddrBydeployerAddr(String deployerAddr, int page, int limit){
        String basePath = String.format(FEESPLIT_DEPLOYER, deployerAddr, page, limit);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }

    //query Contract Addr By Withdrawer Addr
    public static String queryContractAddrByWithdrawerAddr(String withdrawerAddr, int page, int limit) {
        String basePath = String.format(FEESPLIT_WITHDRAWER, withdrawerAddr, page, limit);
        Result res = HttpUtils.httpGetResult(EnvInstance.getEnv().GetRestServerUrl() + EnvInstance.getEnv().GetRestPathPrefix() + basePath);
        if (res.isSuccess()) {
            return res.getData();
        }
        return null;
    }
}
