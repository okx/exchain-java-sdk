package com.okexchain.legacy.crypto.io.cosmos.api;

import java.util.ArrayList;
import java.util.List;

import com.okexchain.legacy.crypto.io.cosmos.types.transferMulti.CosmosTransactionMulti;
import com.okexchain.legacy.crypto.io.cosmos.types.transferMulti.CosmosValueMulti;
import com.okexchain.legacy.crypto.io.cosmos.types.transferMulti.TransferMessageMulti;
import com.okexchain.legacy.crypto.io.cosmos.types.CosmosAccount;
import com.okexchain.legacy.crypto.io.cosmos.types.CosmosData;
import com.okexchain.legacy.crypto.io.cosmos.types.CosmosSignData;
import com.okexchain.legacy.crypto.io.cosmos.types.Fee;
import com.okexchain.legacy.crypto.io.cosmos.types.InputOutput;
import com.okexchain.legacy.crypto.io.cosmos.types.Signature;
import com.okexchain.legacy.crypto.io.cosmos.types.Token;
import com.okexchain.legacy.crypto.io.cosmos.types.ValueMulti;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-01-14 11:38
 **/
public class CosmosTransactionImpl {

    public static CosmosSignData buildSignData(String chainId, Fee fee, String memo, List<CosmosAccount> accountFrom) {
        CosmosSignData cosmosSignData = new CosmosSignData(chainId, fee, memo, accountFrom);
        return cosmosSignData;
    }

    public static String buildTransaction(String chainId, String tranType, String msgType, Fee fee, String memo, List<CosmosAccount> accountFrom,
                                          List<InputOutput> inputs, List<InputOutput> outputs) {
        CosmosSignData cosmosSignData = new CosmosSignData(chainId, fee, memo, accountFrom);

        TransferMessageMulti transferMessageMulti = buildTransferMessage(msgType, inputs, outputs);
        //组装待广播交易结构
        CosmosTransactionMulti cosmosTransaction = new CosmosTransactionMulti();
        cosmosTransaction.setType(tranType);
        CosmosValueMulti cosmosValue = new CosmosValueMulti();
        cosmosValue.setMsgs(new TransferMessageMulti[] {transferMessageMulti});
        cosmosValue.setFee(fee);

        //组装签名结构
        List<Signature> signatures = new ArrayList<>();
        cosmosValue.setSignatures(signatures);

        cosmosValue.setMemo(memo);
        cosmosTransaction.setValue(cosmosValue);

        CosmosData data = new CosmosData(cosmosSignData, cosmosTransaction);

        return data.toJson();
    }

    private static TransferMessageMulti buildTransferMessage(String msgType, List<InputOutput> inputs, List<InputOutput> outputs) {

        ValueMulti valueMulti = new ValueMulti();
        valueMulti.setInputs(inputs);
        valueMulti.setOutputs(outputs);
        TransferMessageMulti transferMessageMulti = new TransferMessageMulti(msgType, valueMulti);
        return transferMessageMulti;
    }

    public static InputOutput buildInputOutput(String address, String amount, String denom) {

        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setAmount(amount);
        token.setDenom(denom);
        tokenList.add(token);

        InputOutput inputOutput = new InputOutput();
        inputOutput.setAddress(address);
        inputOutput.setCoins(tokenList);

        return inputOutput;
    }
}
