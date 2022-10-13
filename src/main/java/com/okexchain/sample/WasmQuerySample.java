package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.wasm.WasmQuery;

import java.io.UnsupportedEncodingException;

public class WasmQuerySample {

    public static void main(String[] args) throws UnsupportedEncodingException {
        //set RestServerUrl and RestPathPrefix
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        EnvInstance.getEnv().setRestPathPrefix("/exchain/v1");

        System.out.println(WasmQuery.queryListCode());
        System.out.println(WasmQuery.queryListContractsByCodeID("77"));
        System.out.println(WasmQuery.queryCodeByCodeID("77"));
        System.out.println(WasmQuery.queryContractByContractAddr("ex14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9s6fqu27"));
        System.out.println(WasmQuery.queryContractHistoryByContractAddr("ex14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9s6fqu27"));
        System.out.println(WasmQuery.queryContractStateAllByContractAddr("ex14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9s6fqu27"));
        System.out.println(WasmQuery.queryContractStateSmart("ex14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9s6fqu27",""));
        System.out.println(WasmQuery.queryContractStateRaw("ex14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9s6fqu27",""));
    }
}
