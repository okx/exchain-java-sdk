package com.okbchain.sample;

import com.okbchain.env.EnvInstance;
import com.okbchain.msg.feesplit.FeesplitQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeesplitQuerySample {

    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        System.out.println(FeesplitQuery.queryFeesplitInfoByContractAddr("0x95d85EC4003A349c05107A0362e846Ca157194A0"));
        System.out.println(FeesplitQuery.queryContractAddrByWithdrawerAddr("ex17d7yg0n5uhqtke865c9t0rehmkjg736c7j47as",1,2));
        System.out.println(FeesplitQuery.queryContractAddrBydeployerAddr("ex1v8segh8mlw297s2ksy6pp6nwxn3el0wmkuqsx2",1,2));
    }
}
