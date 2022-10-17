package com.okexchain.sample;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.wasm.WasmQuery;

import java.io.UnsupportedEncodingException;

public class WasmQuerySample {

    public static void main(String[] args) throws UnsupportedEncodingException {
        //set RestServerUrl and RestPathPrefix
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");
        EnvInstance.getEnv().setRestPathPrefix("/exchain/v1");
        EnvInstance.getEnv().setChainID("exchain-67");
        System.out.println(WasmQuery.queryListCode());
        System.out.println(WasmQuery.queryListContractsByCodeID("4"));
        System.out.println(WasmQuery.queryCodeByCodeID("1"));
        System.out.println(WasmQuery.queryContractByContractAddr("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf"));
        System.out.println(WasmQuery.queryContractHistoryByContractAddr("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf"));
        System.out.println(WasmQuery.queryContractStateAllByContractAddr("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf"));
        System.out.println(WasmQuery.queryContractStateSmart("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf","{\"verifier\":{}}"));
        System.out.println(WasmQuery.queryContractStateRaw("ex1wkwy0xh89ksdgj9hr347dyd2dw7zesmtrue6kfzyml4vdtz6e5wsnw8alf","config"));
    }
}
