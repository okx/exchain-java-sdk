package com.okexchain.legacy.crypto.io.cosmos.types.transferMulti;

import com.okexchain.legacy.crypto.io.cosmos.common.ParameterizedTypeImpl;
import com.okexchain.legacy.crypto.io.cosmos.common.SuccessRespon;
import com.okexchain.legacy.crypto.io.cosmos.common.Utils;
import com.okexchain.legacy.crypto.io.cosmos.types.CosmosTransaction;

import java.lang.reflect.Type;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-25 13:57
 **/
public class CosmosTransactionMulti {

    private String type;

    private CosmosValueMulti value;

    public CosmosTransactionMulti() {

    }
    public CosmosTransactionMulti(String type, CosmosValueMulti value) {
        this.type = type;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setValue(CosmosValueMulti value) {
        this.value = value;
    }

    public CosmosValueMulti getValue() {
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
