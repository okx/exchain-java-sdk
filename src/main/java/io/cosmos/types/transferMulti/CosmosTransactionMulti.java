package io.cosmos.types.transferMulti;

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

/**
 * @program: coin-parent-sdk
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
