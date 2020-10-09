package com.okexchain.legacy.types;

import com.alibaba.fastjson.annotation.JSONField;


public class PostTransaction {
    private StdTransaction tx;

    @JSONField(name = "mode")
    private String returnType;

    public PostTransaction(StdTransaction tx, String returnType) {
        this.tx = tx;
        this.returnType = returnType;
    }

    public void setTx(StdTransaction tx) {
        this.tx = tx;
    }

    public StdTransaction getTx() {
        return tx;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnType() {
        return returnType;
    }


}
