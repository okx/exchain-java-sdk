package com.okexchain.crypto.io.cosmos.types;

import com.okexchain.crypto.io.cosmos.common.ParameterizedTypeImpl;
import com.okexchain.crypto.io.cosmos.common.SuccessRespon;
import com.okexchain.crypto.io.cosmos.common.Utils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-24 09:11
 **/
public class CosmosSignData {

    private String chainId;

    private Fee fee;

    private String memo;

    private List<CosmosAccount> accountList;

    public CosmosSignData(String chainId, Fee fee, String memo, List<CosmosAccount> accountList) {
        this.chainId = chainId;
        this.fee = fee;
        this.memo = memo;
        this.accountList = accountList;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public List<CosmosAccount> getAccountList() {
        return accountList;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public void setAccountList(List<CosmosAccount> accountList) {
        this.accountList = accountList;
    }

    public String toJson() {
        return Utils.serializer.toJson(this);
    }

    public static CosmosSignData fromJson(String json) {
        return Utils.serializer.fromJson(json, CosmosSignData.class);
    }

    public static CosmosSignData fromSuccessRespon(String json) {
        Type responType = new ParameterizedTypeImpl(SuccessRespon.class, new Class[]{CosmosSignData.class});
        SuccessRespon<CosmosSignData> result = Utils.serializer.fromJson(json, responType);
        return result.dataObject;
    }
}
