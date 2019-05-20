package io.cosmos.api;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.cosmos.crypto.Crypto;
import io.cosmos.types.*;
import io.cosmos.types.transferMulti.CosmosTransactionMulti;
import io.cosmos.types.transferMulti.SignDataMulti;
import io.cosmos.util.EncodeUtils;
import org.bitcoinj.core.ECKey;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by liqiang on 2018/10/24.
 */
public class SignMsgSendTest {

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
        value.setToAddress("cosmos1yvkst8hf0v329l8edrjsm0tlkxp9whs4465c5t");
        value.setAmount(amountList);

        transferMessage.setType("cosmos-sdk/MsgSend");
        transferMessage.setValue(value);

        return transferMessage;
    }

    private TransferMessage getMsg2() {
        TransferMessage transferMessage = new TransferMessage();

        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("stake");
        amount.setAmount("1");
        amountList.add(amount);

        Value value = new Value();
        value.setFromAddress("cosmos1yvkst8hf0v329l8edrjsm0tlkxp9whs4465c5t");
        value.setToAddress("cosmos1pn543zt0cyds92wryu4e9rd3m66gsw0tjjvsvx");
        value.setAmount(amountList);

        transferMessage.setType("cosmos-sdk/MsgSend");
        transferMessage.setValue(value);

        return transferMessage;
    }

    private Signature sign(byte[] byteSignData, String privateKey, String pubKey) throws Exception {

        //签名
        byte[] sig = Crypto.sign(byteSignData, privateKey);
        System.out.println(EncodeUtils.bytesToHex(sig));

        String sigResult = Strings.fromByteArray(Base64.encode(sig));
        System.out.println(sigResult);

        Signature signature = new Signature();
        Pubkey pubkey = new Pubkey();
        pubkey.setType("tendermint/PubKeySecp256k1");
        pubkey.setValue(Strings.fromByteArray(
            Base64.encode(Hex.decode(pubKey))));
        signature.setPubkey(pubkey);
        signature.setSignature(sigResult);

        return signature;
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
            TransferMessage transferMessage2 = getMsg2();

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
            signData.setSequence("10");

            //序列化
            byte[] byteSignData = EncodeUtils.toJsonEncodeBytes(signData);
            System.out.println(EncodeUtils.bytesToHex(byteSignData));

            Signature signature = sign(byteSignData, "0771570b347f2dc3b03d64183cc1d708de504a9ee2a1d25048a021ff8920c6dd",
                "039ef93bad5b93f9dfd8bdd8149b5ecb9d571863a1aa165fa7b074a8d6697da5c8");

            SignData signData2 = new SignData();
            signData2.setAccountNumber("3");
            signData2.setChainId("testing");
            Fee fee2 = new Fee();
            fee2.setAmount(amountList);
            fee2.setGas("200000");
            signData2.setFee(fee);
            signData2.setMemo("memo");
            signData2.setMsgs(new TransferMessage[] {transferMessage, transferMessage2});
            signData2.setSequence("0");

            //序列化
            byte[] byteSignData2 = EncodeUtils.toJsonEncodeBytes(signData2);
            System.out.println(EncodeUtils.bytesToHex(byteSignData2));

            Signature signature2 = sign(byteSignData2,
                "2ce66ac4159b1e4830139b5a8fdfe8411a9a7a4409d573f9c31cc39090cc7961",
                "024ef5e2ea0de2a05fe1cca176c0ea64bbaa8269ce080c6743329c5170dbdfb7c0");

            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);
            signatures.add(signature2);

            //组装待广播交易结构
            CosmosTransaction cosmosTransaction = new CosmosTransaction();
            cosmosTransaction.setType("auth/StdTx");
            CosmosValue cosmosValue = new CosmosValue();
            cosmosValue.setMsgs(new TransferMessage[] {transferMessage, transferMessage2});
            cosmosValue.setFee(fee);

            cosmosValue.setSignatures(signatures);

            cosmosValue.setMemo("memo");

            cosmosTransaction.setValue(cosmosValue);
            System.out.println(cosmosTransaction.toJson());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    @Test
    public void testSerializeTransferMsg2() {

        try {
            List<Token> amountList = new ArrayList<>();
            Token amount = new Token();
            amount.setDenom("stake");
            amount.setAmount("0");
            amountList.add(amount);

            //组装待签名交易结构
            TransferMessage transferMessage = getMsg2();

            SignData signData = new SignData();
            signData.setAccountNumber("3");
            signData.setChainId("testing");
            Fee fee = new Fee();
            fee.setAmount(amountList);
            fee.setGas("200000");
            signData.setFee(fee);
            signData.setMemo("memo");
            signData.setMsgs(new TransferMessage[] {transferMessage});
            signData.setSequence("5");

            //序列化
            byte[] byteSignData = EncodeUtils.toJsonEncodeBytes(signData);
            System.out.println(EncodeUtils.bytesToHex(byteSignData));

            Signature signature = sign(byteSignData,
                "2ce66ac4159b1e4830139b5a8fdfe8411a9a7a4409d573f9c31cc39090cc7961",
                "024ef5e2ea0de2a05fe1cca176c0ea64bbaa8269ce080c6743329c5170dbdfb7c0");

            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);

            //组装待广播交易结构
            CosmosTransaction cosmosTransaction = new CosmosTransaction();
            cosmosTransaction.setType("auth/StdTx");
            CosmosValue cosmosValue = new CosmosValue();
            cosmosValue.setMsgs(new TransferMessage[] {transferMessage});
            cosmosValue.setFee(fee);

            cosmosValue.setSignatures(signatures);

            cosmosValue.setMemo("memo");

            cosmosTransaction.setValue(cosmosValue);
            System.out.println(cosmosTransaction.toJson());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    @Test
    public void testSign() {
        Token amount = new Token();
        amount.setDenom("stake");
        amount.setAmount("1");
        Fee fee = new Fee();
        fee.setAmount(Collections.singletonList(amount));
        fee.setGas("200000");

        CosmosAccount account = new CosmosAccount("cosmos1c6ympsh9xs4jp2fyjv0mpn2v43lafk7tg76329",
            "7", "0", "44/1044/0/0/1");
        List<CosmosAccount> accountList = Collections.singletonList(account);

        String chainId = "testing";
        String memo = "memo";
        String tranType = "auth/StdTx";
        String msgType= "cosmos-sdk/MsgMultiSend";

        InputOutput input = CosmosTransactionImpl.buildInputOutput("cosmos1c6ympsh9xs4jp2fyjv0mpn2v43lafk7tg76329",
            "2", "stake");
        InputOutput output = CosmosTransactionImpl.buildInputOutput("cosmos1w66q93gxv0dt0y0drll9xrtwrkxest50lg7a35",
            "1", "stake");
        InputOutput output1 = CosmosTransactionImpl.buildInputOutput(
            "cosmos1yqv34fhf5z2mlcw7cu2z88uv5tcv5pvy0c8226", "1", "stake");
        List<InputOutput> inputList = new ArrayList<>();
        inputList.add(input);
        List<InputOutput> outputList = new ArrayList<>();
        outputList.add(output);
        outputList.add(output1);

        String transaction = CosmosTransactionImpl.buildTransaction(chainId, tranType, msgType, fee, memo, accountList, inputList,
            outputList);

        //签名组装交易
        try {
            CosmosData data = CosmosData.fromJson(transaction);
            CosmosTransactionMulti tx = data.getTransactions();
            CosmosSignData cosmosSignData = data.getSignData();

            for (int i = 0; i < accountList.size(); i++) {
                final CosmosAccount cosmosAccount = accountList.get(i);
                final String[] chainPathArr = cosmosAccount.getChainPath().split("/");

                Integer addressIndex = cosmosAccount.getChainPath().startsWith("/") ? Integer.valueOf(chainPathArr[5]) : Integer.valueOf(chainPathArr[4]);
                byte[] priKeyVal = Hex.decode("966527096170ce7877e17fe4022163ac7e55ee15c56e12348f1bddfd8d05ccbc");
                byte[] pubKeyVal = Hex.decode("038e6c2356374eddc0e398750670e1a7c8962bb9661671737cc6e7708fd4af09ab");

                SignDataMulti signData = new SignDataMulti(cosmosAccount.getAccountNumber(), cosmosSignData.getChainId(),
                    cosmosSignData.getFee(),cosmosSignData.getMemo(),tx.getValue().getMsgs(),cosmosAccount.getSequence());
                //序列化
                byte[] byteSignData = EncodeUtils.toJsonEncodeBytes(signData);
                //签名
                byte[] sig = Crypto.sign(EncodeUtils.hexStringToByteArray(EncodeUtils.bytesToHex(byteSignData)),
                    Hex.toHexString(priKeyVal));
                String sigResult = Strings.fromByteArray(Base64.encode(sig));
                System.out.println("The result is: " + sigResult);    //expected: /hRJwaM+SH0kLb8x9Uy4TnSeSlnyYzUGKaERQF1ABmMOXA56l7NhphVUISAeyQx06pMHbipaPq4gr6++SCVtpg==
                Signature signature = new Signature();
                Pubkey pubkey = new Pubkey(Strings.fromByteArray(
                    Base64.encode(pubKeyVal)));
                signature.setPubkey(pubkey);
                signature.setSignature(sigResult);
                tx.getValue().getSignatures().add(signature);
            }
            System.out.println( "sign transaction success!");
        } catch (Exception e) {
            System.out.println( "sign transaction failed!");
        }
    }
}
