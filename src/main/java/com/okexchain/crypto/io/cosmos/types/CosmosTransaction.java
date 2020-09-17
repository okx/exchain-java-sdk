package com.okexchain.crypto.io.cosmos.types;

import com.okexchain.crypto.io.cosmos.common.ParameterizedTypeImpl;
import com.okexchain.crypto.io.cosmos.common.SuccessRespon;
import com.okexchain.crypto.io.cosmos.common.Utils;

import java.lang.reflect.Type;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-01-14 11:38
 **/
public class CosmosTransaction {

    private String type;

    private CosmosValue value;

    public CosmosTransaction() {

    }
    public CosmosTransaction(String type, CosmosValue value) {
        this.type = type;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setValue(CosmosValue value) {
        this.value = value;
    }

    public CosmosValue getValue() {
        return value;
    }

    public String toJson() {
        return Utils.serializer.toJson(this);
    }

    public static CosmosTransaction fromJson(String json) {
        return Utils.serializer.fromJson(json, CosmosTransaction.class);
    }

    public static CosmosTransaction fromSuccessRespon(String json) {
        Type responType = new ParameterizedTypeImpl(SuccessRespon.class, new Class[]{CosmosTransaction.class});
        SuccessRespon<CosmosTransaction> result = Utils.serializer.fromJson(json, responType);
        return result.dataObject;
    }
}
