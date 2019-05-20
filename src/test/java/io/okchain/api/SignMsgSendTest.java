package io.okchain.api;

import io.cosmos.api.CosmosTransactionImpl;
import io.cosmos.crypto.Crypto;
import io.cosmos.types.*;
import io.cosmos.types.transferMulti.CosmosTransactionMulti;
import io.cosmos.types.transferMulti.SignDataMulti;
import io.cosmos.util.EncodeUtils;
import io.okchain.types.OKChainTransaction;
import io.okchain.types.OKChainValue;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);

        Value value = new Value();
        value.setFromAddress("okchain152p8xmejhza7wuhhzut88vkakdgasqwlw2qjcf");
        value.setToAddress("okchain1t2cvfv58764q4wdly7qjx5d2z89lewvwq2448n");
        value.setAmount(amountList);

        transferMessage.setType("cosmos-sdk/MsgSend");
        transferMessage.setValue(value);

        return transferMessage;
    }

    private Signature sign(byte[] byteSignData, String privateKey) throws Exception {

        //签名
        byte[] sig = Crypto.sign(byteSignData, privateKey);
        //System.out.println(EncodeUtils.bytesToHex(sig));



        String sigResult = Strings.fromByteArray(Base64.encode(sig));
        //System.out.println(sigResult);

        Signature signature = new Signature();
        Pubkey pubkey = new Pubkey();
        pubkey.setType("tendermint/PubKeySecp256k1");
        pubkey.setValue(Strings.fromByteArray(
                Base64.encode(Hex.decode(Crypto.generatePubKeyFromPriv(privateKey)))));
        signature.setPubkey(pubkey);
        signature.setSignature(sigResult);

        return signature;
    }


    @Test
    public void testBuildTransferMsg() {

        try {
            List<Token> amountList = new ArrayList<>();
            Token amount = new Token();
            amount.setDenom("okb");
            amount.setAmount("1.00000000");
            amountList.add(amount);
            String memo = "ming tian ye yao jia you ya";

            //组装待签名交易结构
            TransferMessage transferMessage = getMsg();

            SignData signData = new SignData();
            signData.setAccountNumber("4");
            signData.setChainId("okchain");
            Fee fee = new Fee();
            fee.setAmount(amountList);
            fee.setGas("200000");
            signData.setFee(fee);
            signData.setMemo(memo);
            signData.setMsgs(new TransferMessage[] {transferMessage});
            signData.setSequence("44");

            //序列化
            byte[] byteSignData = EncodeUtils.toJsonEncodeBytes(signData);
            //System.out.println(EncodeUtils.bytesToHex(byteSignData));
            System.out.println("signMsg: "+new String(byteSignData));

            Signature signature = sign(byteSignData, "c4c451ce673485521f9c9b74b6d90f0da433ef7f012fa7f9db4def627dccd632");

            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);

            //组装待广播交易结构
            OKChainTransaction oKChainTransaction = new OKChainTransaction();
            oKChainTransaction.setReturnType("block");
            OKChainValue oKChainValue = new OKChainValue();
            oKChainValue.setMsgs(new TransferMessage[] {transferMessage});
            oKChainValue.setFee(fee);

            oKChainValue.setSignatures(signatures);

            oKChainValue.setMemo(memo);

            oKChainTransaction.setTx(oKChainValue);
            System.out.println("Tx: "+oKChainTransaction.toJson());

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

}
