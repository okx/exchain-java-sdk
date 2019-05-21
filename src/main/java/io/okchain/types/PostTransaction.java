package io.okchain.types;

import com.google.gson.annotations.SerializedName;
import io.cosmos.common.ParameterizedTypeImpl;
import io.cosmos.common.SuccessRespon;
import io.cosmos.common.Utils;
import io.cosmos.exception.CosmosException;
import io.cosmos.http.Client;
import io.cosmos.types.CosmosTransaction;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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

    private static Logger logger = Logger.getLogger(CosmosTransaction.class);

    public String toJson() {
        return Utils.serializer.toJson(this);
    }

    public static CosmosTransaction fromJson(String json) {
        return Utils.serializer.fromJson(json, CosmosTransaction.class);
    }

    public static PostTransaction fromSuccessRespon(String json) {
        Type responType = new ParameterizedTypeImpl(SuccessRespon.class, new Class[]{PostTransaction.class});
        SuccessRespon<PostTransaction> result = Utils.serializer.fromJson(json, responType);
        return result.dataObject;
    }

    public static PostTransaction decode(Client client, String txId) throws CosmosException {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("raw_transaction", txId);
        PostTransaction PostTransaction =
                client.request("decode-raw-transaction", req, CosmosTransaction.class);

        logger.info("decode-raw-transaction:");
        logger.info(PostTransaction.toJson());

        return PostTransaction;
    }
}
