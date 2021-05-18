package com.okexchain.utils.evm;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author shaoyun.zhan
 * @date 2021/5/17
 * <p>
 * desc：webj3 operate example
 */
public class ContractUtil {


    /**
     * ethCall
     * @param web3j
     * @param fromAddress
     * @param contractAddress
     * @param methodName
     * @param inputParameters
     * @param outputParameters
     * @return
     */
    public static List<Type> ethCall(Web3j web3j, String fromAddress, String contractAddress, String methodName, List<Type> inputParameters, List<TypeReference<?>> outputParameters) {
        Function function = new Function(methodName, inputParameters, outputParameters);
        String data = FunctionEncoder.encode(function);
        Transaction transaction = Transaction.createEthCallTransaction(fromAddress, contractAddress, data);

        EthCall ethCall;
        try {
            ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();
            List<Type> results = FunctionReturnDecoder.decode(ethCall.getValue(), function.getOutputParameters());
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<>();
    }

    /**
     * ethSendTransaction
     * @param web3j
     * @param transaction
     * @return
     * @throws Exception
     */
    public static EthSendTransaction ethSendTransaction(Web3j web3j, Transaction transaction) throws Exception {
        EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).sendAsync().get();
        return ethSendTransaction;
    }

    /**
     * ethSendRawTransaction
     * @param web3j
     * @param hexValue
     * @return
     * @throws Exception
     */
    public static EthSendTransaction ethSendRawTransaction(Web3j web3j,  String hexValue) throws Exception {
        EthSendTransaction ethSendTransaction =  web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        return ethSendTransaction;
    }
}
