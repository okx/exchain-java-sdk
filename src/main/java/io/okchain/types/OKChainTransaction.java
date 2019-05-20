package io.okchain.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.cosmos.common.ParameterizedTypeImpl;
import io.cosmos.common.SuccessRespon;
import io.cosmos.common.Utils;
import io.cosmos.exception.CosmosException;
import io.cosmos.http.Client;
import io.cosmos.types.CosmosTransaction;
import io.cosmos.types.CosmosValue;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class OKChainTransaction {
    private OKChainValue tx;

    @SerializedName(value = "return")
    private String returnType;

    public OKChainTransaction() {

    }
    public OKChainTransaction(OKChainValue tx, String returnType) {
        this.tx = tx;
        this.returnType = returnType;
    }

    public void setTx(OKChainValue tx) {
        this.tx = tx;
    }

    public OKChainValue getTx() {
        return tx;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnType() {
        return returnType;
    }

    private static Logger logger = Logger.getLogger(CosmosTransaction.class);

    public String toJson() {
        return Utils.serializer.toJson(this);
    }

    public static CosmosTransaction fromJson(String json) {
        return Utils.serializer.fromJson(json, CosmosTransaction.class);
    }

    public static OKChainTransaction fromSuccessRespon(String json) {
        Type responType = new ParameterizedTypeImpl(SuccessRespon.class, new Class[]{OKChainTransaction.class});
        SuccessRespon<OKChainTransaction> result = Utils.serializer.fromJson(json, responType);
        return result.dataObject;
    }

    public static OKChainTransaction decode(Client client, String txId) throws CosmosException {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("raw_transaction", txId);
        OKChainTransaction OKChainTransaction =
                client.request("decode-raw-transaction", req, CosmosTransaction.class);

        logger.info("decode-raw-transaction:");
        logger.info(OKChainTransaction.toJson());

        return OKChainTransaction;
    }
}
