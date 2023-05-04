package com.okbchain.sample;

import com.okbchain.env.EnvInstance;
import com.okbchain.msg.wasm.WasmQuery;

import java.io.UnsupportedEncodingException;

public class WasmQuerySample {

    public static void main(String[] args) throws UnsupportedEncodingException {
        EnvInstance.getEnvLocalNet();
//        System.out.println(WasmQuery.queryListCode());
//        System.out.println(WasmQuery.queryListContractsByCodeID("1"));
//        System.out.println(WasmQuery.queryCodeByCodeID("1"));
        System.out.println(WasmQuery.queryContractByContractAddr("ex14hj2tavq8fpesdwxxcu44rty3hh90vhujrvcmstl4zr3txmfvw9s6fqu27"));
//        System.out.println(WasmQuery.queryContractHistoryByContractAddr("ex1xt4ahzz2x8hpkc0tk6ekte9x6crw4w6u0r67cyt3kz9syh24pd7spvs7pk"));
//        System.out.println(WasmQuery.queryContractStateAllByContractAddr("ex1xt4ahzz2x8hpkc0tk6ekte9x6crw4w6u0r67cyt3kz9syh24pd7spvs7pk"));
//        System.out.println(WasmQuery.queryContractStateSmart("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf","{\"verifier\":{}}"));
//        System.out.println(WasmQuery.queryContractStateRaw("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf","config"));
    }
}
