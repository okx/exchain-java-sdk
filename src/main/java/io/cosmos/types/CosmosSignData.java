package io.cosmos.types;

import io.cosmos.common.ParameterizedTypeImpl;
import io.cosmos.common.SuccessRespon;
import io.cosmos.common.Utils;
import io.cosmos.exception.CosmosException;
import io.cosmos.http.Client;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: coin-parent-sdk
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

    private static Logger logger = Logger.getLogger(CosmosTransaction.class);

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

    public static CosmosSignData decode(Client client, String txId) throws CosmosException {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("raw_transaction", txId);
        CosmosSignData cosmosSignData =
            client.request("decode-raw-transaction", req, CosmosSignData.class);

        logger.info("decode-raw-transaction:");
        logger.info(cosmosSignData.toJson());

        return cosmosSignData;
    }
}
