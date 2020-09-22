package io.cosmos.msg.utils;

import io.cosmos.common.Utils;


public class BoardcastTx {

    private String mode;
    private TxValue tx;

    public BoardcastTx() {
    }

    public TxValue getTx() {
        return tx;
    }

    public void setTx(TxValue tx) {
        this.tx = tx;
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
