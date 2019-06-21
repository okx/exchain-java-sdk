package com.okchain.types;

public class MsgStd implements IMsg {
    public MsgStd() {
    }

    public MsgStd(String type, IMsg value) {
        this.type = type;
        this.value = value;
    }

    private String type;
    private IMsg value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public IMsg getValue() {
        return value;
    }

    public void setValue(IMsg value) {
        this.value = value;
    }
}
