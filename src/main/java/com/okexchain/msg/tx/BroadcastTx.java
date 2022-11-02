package com.okexchain.msg.tx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okexchain.utils.Utils;
import com.okexchain.msg.common.TxValue;

import java.io.IOException;


public class BroadcastTx {

    private String mode;
    private String type = "cosmos-sdk/StdTx";
    private TxValue tx;

    public BroadcastTx() {
    }

    public TxValue getTx() {
        return tx;
    }

    public void setTx(TxValue value) {
        this.tx = value;
    }


//  public String toJson() {
//        return Utils.serializer.toJson(this);
//  }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

//    public static BroadcastTx fromJson(String json) {
//        return Utils.serializer.fromJson(json, BroadcastTx.class);
//    }

    public static BroadcastTx fromJson(String json) throws IOException {
        return new ObjectMapper().readValue(json,BroadcastTx.class);
    }

}
