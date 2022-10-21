package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.feesplit.FeesplitQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeesplitQuerySample {

    public static void main(String[] args) {
        EnvInstance.getEnvTestNet();
        System.out.println(FeesplitQuery.queryFeesplitInfoByContractAddr("0x95d85EC4003A349c05107A0362e846Ca157194A0"));
        System.out.println(FeesplitQuery.queryContractAddrByWithdrawerAddr("ex17d7yg0n5uhqtke865c9t0rehmkjg736c7j47as"));
    }
}
