package io.cosmos.api;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.cosmos.crypto.Crypto;
import io.cosmos.types.CosmosAccount;
import io.cosmos.types.CosmosTransaction;
import io.cosmos.types.CosmosValue;
import io.cosmos.types.Fee;
import io.cosmos.types.InputOutput;
import io.cosmos.types.Pubkey;
import io.cosmos.types.SignData;
import io.cosmos.types.Signature;
import io.cosmos.types.Token;
import io.cosmos.types.TransferMessage;
import io.cosmos.types.Value;
import io.cosmos.types.ValueMulti;
import io.cosmos.types.transferMulti.CosmosTransactionMulti;
import io.cosmos.types.transferMulti.CosmosValueMulti;
import io.cosmos.types.transferMulti.SignDataMulti;
import io.cosmos.types.transferMulti.TransferMessageMulti;
import io.cosmos.util.EncodeUtils;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

/**
 * Created by liqiang on 2018/10/24.
 */
public class SignTransactionTest {

    @Test
    public void testSignCosmosTransaction() throws NoSuchAlgorithmException {
        String hex = "";
        byte[] sig = Crypto.sign(EncodeUtils.hexStringToByteArray(hex), "");
        System.out.println(EncodeUtils.bytesToHex(sig));
    }

    private TransferMessage getMsg() {
        TransferMessage transferMessage = new TransferMessage();

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("stake");
        amount.setAmount("1");
        amountList.add(amount);

        Value value = new Value();
        value.setFromAddress("cosmos1javjfas9g5lrtths403er5kjtzuf9dy6x990kn");
        value.setToAddress("cosmos1p0w9dwkgteyd8298tn3sed32g5q9ny7x62ejv4");
        value.setAmount(amountList);

        transferMessage.setType("cosmos-sdk/MsgSend");
        transferMessage.setValue(value);

        return transferMessage;
    }

    private TransferMessageMulti getMsgMulti() {
        TransferMessageMulti transferMessageMulti = new TransferMessageMulti();

        InputOutput input = getInputOutput("cosmos1javjfas9g5lrtths403er5kjtzuf9dy6x990kn");
        InputOutput input1 = getInputOutput("cosmos1yvkst8hf0v329l8edrjsm0tlkxp9whs4465c5t");

        InputOutput output = getInputOutput("cosmos1l5yee3wk5dmq2fz2890tcpyc5zf3jey29ajkyd");
        InputOutput output1 = getInputOutput("cosmos1l5yee3wk5dmq2fz2890tcpyc5zf3jey29ajkyd");

        List<InputOutput> inputList = new ArrayList<>();
        inputList.add(input);
        inputList.add(input1);

        List<InputOutput> outputList = new ArrayList<>();
        outputList.add(output);
        outputList.add(output1);

        ValueMulti valueMulti = new ValueMulti();
        valueMulti.setInputs(inputList);
        valueMulti.setOutputs(outputList);

        transferMessageMulti.setType("cosmos-sdk/MsgMultiSend");
        transferMessageMulti.setValue(valueMulti);

        return transferMessageMulti;
    }

    private TransferMessageMulti getMsgMulti1() {
        TransferMessageMulti transferMessageMulti = new TransferMessageMulti();

        InputOutput input = getInputOutput("cosmos1javjfas9g5lrtths403er5kjtzuf9dy6x990kn");
        InputOutput input1 = getInputOutput("cosmos1p0w9dwkgteyd8298tn3sed32g5q9ny7x62ejv4");

        InputOutput output = getInputOutput("cosmos1l5yee3wk5dmq2fz2890tcpyc5zf3jey29ajkyd");
        InputOutput output1 = getInputOutput("cosmos1l5yee3wk5dmq2fz2890tcpyc5zf3jey29ajkyd");

        List<InputOutput> inputList = new ArrayList<>();
        inputList.add(input);
        inputList.add(input1);

        List<InputOutput> outputList = new ArrayList<>();
        outputList.add(output);
        outputList.add(output1);

        ValueMulti valueMulti = new ValueMulti();
        valueMulti.setInputs(inputList);
        valueMulti.setOutputs(outputList);

        transferMessageMulti.setType("cosmos-sdk/MsgMultiSend");
        transferMessageMulti.setValue(valueMulti);

        return transferMessageMulti;
    }

    @Test
    public void testSerializeTransferMsgMulti() {

        try {
            List<Token> amountList = new ArrayList<>();
            Token amount = new Token();
            amount.setDenom("stake");
            amount.setAmount("1");
            amountList.add(amount);

            //组装待签名交易结构
            TransferMessageMulti transferMessageMulti = getMsgMulti();
            TransferMessageMulti transferMessageMulti1 = getMsgMulti1();

            SignDataMulti signData = new SignDataMulti();
            signData.setAccountNumber("2");
            signData.setChainId("testing");
            Fee fee = new Fee();
            fee.setAmount(amountList);
            fee.setGas("200000");
            signData.setFee(fee);
            signData.setMemo("memo");
            signData.setMsgs(new TransferMessageMulti[] {transferMessageMulti, transferMessageMulti1});
            signData.setSequence("13");
            //序列化
            byte[] byteSignData = EncodeUtils.toJsonEncodeBytes(signData);
            //签名
            byte[] sig = Crypto.sign(EncodeUtils.hexStringToByteArray(EncodeUtils.bytesToHex(byteSignData)),
                "0771570b347f2dc3b03d64183cc1d708de504a9ee2a1d25048a021ff8920c6dd");
            String sigResult = Strings.fromByteArray(Base64.encode(sig));

            SignDataMulti signData1 = new SignDataMulti();
            signData1.setAccountNumber("3");
            signData1.setChainId("testing");
            signData1.setFee(fee);
            signData1.setMemo("memo");
            signData1.setMsgs(new TransferMessageMulti[] {transferMessageMulti, transferMessageMulti1});
            signData1.setSequence("1");
            //序列化
            byte[] byteSignData1 = EncodeUtils.toJsonEncodeBytes(signData1);
            //签名
            byte[] sig1 = Crypto.sign(EncodeUtils.hexStringToByteArray(EncodeUtils.bytesToHex(byteSignData1)),
                "2ce66ac4159b1e4830139b5a8fdfe8411a9a7a4409d573f9c31cc39090cc7961");
            String sigResult1 = Strings.fromByteArray(Base64.encode(sig1));

            SignDataMulti signData3 = new SignDataMulti();
            signData3.setAccountNumber("5");
            signData3.setChainId("testing");
            signData3.setFee(fee);
            signData3.setMemo("memo");
            signData3.setMsgs(new TransferMessageMulti[] {transferMessageMulti, transferMessageMulti1});
            signData3.setSequence("0");

            //序列化
            byte[] byteSignData3 = EncodeUtils.toJsonEncodeBytes(signData3);
            //签名
            byte[] sig3 = Crypto.sign(EncodeUtils.hexStringToByteArray(EncodeUtils.bytesToHex(byteSignData3)),
                "d53aca7dda1e6e07fd481767fee92ad6dfc51942b1395fd57308294607cce7cc");
            String sigResult3 = Strings.fromByteArray(Base64.encode(sig3));

            //组装待广播交易结构
            CosmosTransactionMulti cosmosTransaction = new CosmosTransactionMulti();
            cosmosTransaction.setType("auth/StdTx");
            CosmosValueMulti cosmosValue = new CosmosValueMulti();
            cosmosValue.setMsgs(new TransferMessageMulti[] {transferMessageMulti, transferMessageMulti1});
            cosmosValue.setFee(fee);

            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            Signature signature = new Signature();
            Pubkey pubkey = new Pubkey();
            pubkey.setType("tendermint/PubKeySecp256k1");
            pubkey.setValue(Strings.fromByteArray(
                Base64.encode(Hex.decode("039ef93bad5b93f9dfd8bdd8149b5ecb9d571863a1aa165fa7b074a8d6697da5c8"))));
            signature.setPubkey(pubkey);
            signature.setSignature(sigResult);
            signatures.add(signature);

            Signature signature1 = new Signature();
            Pubkey pubkey1 = new Pubkey();
            pubkey1.setType("tendermint/PubKeySecp256k1");
            pubkey1.setValue(Strings.fromByteArray(
                Base64.encode(Hex.decode("024ef5e2ea0de2a05fe1cca176c0ea64bbaa8269ce080c6743329c5170dbdfb7c0"))));
            signature1.setPubkey(pubkey1);
            signature1.setSignature(sigResult1);
            signatures.add(signature1);

            Signature signature3 = new Signature();
            Pubkey pubkey3 = new Pubkey();
            pubkey3.setType("tendermint/PubKeySecp256k1");
            pubkey3.setValue(Strings.fromByteArray(
                Base64.encode(Hex.decode("02141202d82740af1b754b442f5fd9c4a862d49b5cafca5d04a9f06216ddf545bd"))));
            signature3.setPubkey(pubkey3);
            signature3.setSignature(sigResult3);
            signatures.add(signature3);

            cosmosValue.setSignatures(signatures);

            cosmosValue.setMemo("memo");
            cosmosTransaction.setValue(cosmosValue);
            System.out.println(cosmosTransaction.toJson());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    @Test
    public void testSerializeTransferMsg() {

        try {
            List<Token> amountList = new ArrayList<>();
            Token amount = new Token();
            amount.setDenom("stake");
            amount.setAmount("1");
            amountList.add(amount);

            //组装待签名交易结构
            TransferMessage transferMessage = getMsg();
            TransferMessage transferMessage2 = getMsg();

            byte[] test = EncodeUtils.toJsonEncodeBytes(transferMessage);
            System.out.println(EncodeUtils.bytesToHex(test));

            SignData signData = new SignData();
            signData.setAccountNumber("2");
            signData.setChainId("testing");
            Fee fee = new Fee();
            fee.setAmount(amountList);
            fee.setGas("200000");
            signData.setFee(fee);
            signData.setMemo("memo");
            signData.setMsgs(new TransferMessage[] {transferMessage, transferMessage2});
            signData.setSequence("12");

            //序列化
            byte[] byteSignData = EncodeUtils.toJsonEncodeBytes(signData);
            System.out.println(EncodeUtils.bytesToHex(byteSignData));

            //签名
            byte[] sig = Crypto.sign(EncodeUtils.hexStringToByteArray(EncodeUtils.bytesToHex(byteSignData)),
                "0771570b347f2dc3b03d64183cc1d708de504a9ee2a1d25048a021ff8920c6dd");
            System.out.println(EncodeUtils.bytesToHex(sig));

            String sigResult = Strings.fromByteArray(Base64.encode(sig));
            System.out.println(sigResult);

            //组装待广播交易结构
            CosmosTransaction cosmosTransaction = new CosmosTransaction();
            cosmosTransaction.setType("auth/StdTx");
            CosmosValue cosmosValue = new CosmosValue();
            cosmosValue.setMsgs(new TransferMessage[] {transferMessage, transferMessage2});
            cosmosValue.setFee(fee);

            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            Signature signature = new Signature();
            Pubkey pubkey = new Pubkey();
            pubkey.setType("tendermint/PubKeySecp256k1");
            pubkey.setValue(Strings.fromByteArray(
                Base64.encode(Hex.decode("039ef93bad5b93f9dfd8bdd8149b5ecb9d571863a1aa165fa7b074a8d6697da5c8"))));
            signature.setPubkey(pubkey);
            signature.setSignature(sigResult);
            signatures.add(signature);

            cosmosValue.setSignatures(signatures);

            cosmosValue.setMemo("memo");
            cosmosTransaction.setValue(cosmosValue);
            System.out.println(cosmosTransaction.toJson());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    private InputOutput getInputOutput(String address) {

        List<Token> tokenList = new ArrayList<>();
        Token token = new Token();
        token.setAmount("1");
        token.setDenom("stake");
        tokenList.add(token);

        InputOutput inputOutput = new InputOutput();
        inputOutput.setAddress(address);
        inputOutput.setCoins(tokenList);

        return inputOutput;
    }

    @Test
    public void testBuildCosmosTransaction() {

        try {
            //fee
            Token amount = new Token("1", "stake");
            Fee fee = new Fee();
            fee.setAmount(Collections.singletonList(amount));
            fee.setGas("200000");

            //账户信息
            CosmosAccount account = new CosmosAccount("cosmos1javjfas9g5lrtths403er5kjtzuf9dy6x990kn",
                "2", "15", "44/1042/0/0/1");
            List<CosmosAccount> accountList = Collections.singletonList(account);

            String chainId = "testing";
            String memo = "memo";

            //输入输出
            InputOutput input = CosmosTransactionImpl.buildInputOutput("cosmos1javjfas9g5lrtths403er5kjtzuf9dy6x990kn",
                "2", "stake");
            InputOutput output = CosmosTransactionImpl.buildInputOutput("cosmos1yvkst8hf0v329l8edrjsm0tlkxp9whs4465c5t",
                "1", "stake");
            InputOutput output1 = CosmosTransactionImpl.buildInputOutput(
                "cosmos1p0w9dwkgteyd8298tn3sed32g5q9ny7x62ejv4", "1", "stake");
            List<InputOutput> inputList = new ArrayList<>();
            inputList.add(input);
            List<InputOutput> outputList = new ArrayList<>();
            outputList.add(output);
            outputList.add(output1);

            String tranType = "auth/StdTx";
            String msgType = "cosmos-sdk/MsgMultiSend";

            String transaction = CosmosTransactionImpl.buildTransaction(chainId, tranType, msgType, fee, memo, accountList, inputList,
                outputList);
            System.out.println(transaction);

        } catch (Exception e) {
            System.out.println("build transaction failed");
        }
    }
}
