package com.okexchain.types;


import com.alibaba.fastjson.annotation.JSONField;

public class BaseModel {

    private int code;
    private String data;
    private String msg;

    @JSONField(name = "detail_msg")
    private String detailMsg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "code=" + code +
                ", data='" + data + '\'' +
                ", msg='" + msg + '\'' +
                ", detail_msg='" + detailMsg + '\'' +
                '}';
    }
}
