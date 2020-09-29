package io.okexchain.types;

public class MsgStd implements IMsg {
    private String type;
    private IMsg value;

    public MsgStd(String type, IMsg value) {
        this.type = type;
        this.value = value;
    }


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
