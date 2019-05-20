package io.cosmos.types;

import com.google.gson.annotations.SerializedName;
import io.cosmos.common.ExpandedPrivateKey;
import io.cosmos.common.ParameterizedTypeImpl;
import io.cosmos.common.SuccessRespon;
import io.cosmos.common.Utils;
import io.cosmos.exception.CosmosException;
import io.cosmos.http.Client;
import io.cosmos.types.*;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @program: coin-parent-sdk
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

    private static Logger logger = Logger.getLogger(CosmosTransaction.class);

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

    public static CosmosTransaction decode(Client client, String txId) throws CosmosException {
        Map<String, Object> req = new HashMap<String, Object>();
        req.put("raw_transaction", txId);
        CosmosTransaction CosmosTransaction =
            client.request("decode-raw-transaction", req, CosmosTransaction.class);

        logger.info("decode-raw-transaction:");
        logger.info(CosmosTransaction.toJson());

        return CosmosTransaction;
    }
}
