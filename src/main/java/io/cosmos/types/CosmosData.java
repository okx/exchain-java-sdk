package io.cosmos.types;

import io.cosmos.common.Utils;
import io.cosmos.types.transferMulti.CosmosTransactionMulti;
import org.apache.log4j.Logger;

/**
 * @program: coin-parent-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-25 12:05
 **/
public class CosmosData {

    private CosmosSignData signData;

    private CosmosTransactionMulti transactions;

    public CosmosData(CosmosSignData signData, CosmosTransactionMulti transactions) {
        this.signData = signData;
        this.transactions = transactions;
    }

    public void setSignData(CosmosSignData signData) {
        this.signData = signData;
    }

    public CosmosSignData getSignData() {
        return signData;
    }

    public CosmosTransactionMulti getTransactions() {
        return transactions;
    }

    public void setTransactions(CosmosTransactionMulti transactions) {
        this.transactions = transactions;
    }

    private static Logger logger = Logger.getLogger(CosmosData.class);

    public String toJson() {
        return Utils.serializer.toJson(this);
    }

    public static CosmosData fromJson(String json) {
        return Utils.serializer.fromJson(json, CosmosData.class);
    }
}
