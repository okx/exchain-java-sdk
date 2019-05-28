package io.okchain.types;

import com.google.gson.annotations.SerializedName;
import io.okchain.common.Utils;


public class PostTransaction {
    private StdTransaction tx;

    @SerializedName(value = "return")
    private String returnType;

    public PostTransaction() {

    }
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

    public String toJson() {
        return Utils.serializer.toJson(this);
    }


}
