package com.okexchain.msg.tx;

import com.okexchain.utils.Utils;
import com.okexchain.msg.common.TxValue;


public class BoardcastTx {

    private String mode;
    private String type = "cosmos-sdk/StdTx";
    private TxValue value;

    public BoardcastTx() {
    }

    public TxValue getTx() {
        return value;
    }

    public void setTx(TxValue value) {
        this.value = value;
    }

    public String toJson() {
        return Utils.serializer.toJson(this);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public static BoardcastTx fromJson(String json) {
        return Utils.serializer.fromJson(json, BoardcastTx.class);
    }
}
