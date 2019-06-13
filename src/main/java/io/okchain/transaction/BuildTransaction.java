package io.okchain.transaction;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.okchain.common.ConstantIF;
import io.okchain.crypto.Crypto;
import io.okchain.types.*;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.List;

public class BuildTransaction {
    public static String generatePlaceOrderTransaction(AccountInfo account, String side, String product, String price, String quantity, String memo) {

        IMsg msg = new MsgNewOrder("", "", price, product, quantity, account.getUserAddress(), side);
        IMsg stdMsg = new MsgStd("order/new", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    public static String generateCancelOrderTransaction(AccountInfo account, String orderId, String memo) {
        IMsg msg = new MsgCancelOrder(account.getUserAddress(), orderId);
        IMsg stdMsg = new MsgStd("order/cancel", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    public static String generateSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) {
        IMsg msg = new MsgSend(account.getUserAddress(), to, amount);
        IMsg stdMsg = new MsgStd("token/Send", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    public static String generateMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) {
        IMsg msg = new MsgMultiSend(account.getUserAddress(), transfers);
        IMsg stdMsg = new MsgStd("token/MultiSend", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }


    private static String buildTransaction(AccountInfo account, IMsg stdMsg, IMsg signMsg, String memo) {
        if (account.getAccountNumber() == "" || account.getSequenceNumber() == "") {

        }
        if (memo == null) {
            memo = "";
        }
        Fee fee = generateFeeDefault();
        SignData signData = new SignData(account.getAccountNumber(), ConstantIF.CHAIN_ID, fee, memo, new IMsg[]{signMsg}, account.getSequenceNumber());
        try {
            String signDataJson = JSONObject.toJSON(signData).toString();
            System.out.println("signData: " + signDataJson);
            Signature signature = sign(signDataJson.getBytes(), account.getPrivateKey());
            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);
            StdTransaction stdTransaction = new StdTransaction(new IMsg[]{stdMsg}, fee, signatures, memo);
            //组装待广播交易结构

            PostTransaction postTransaction = new PostTransaction(stdTransaction, "block");
            return JSON.toJSON(postTransaction).toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    private static Fee generateFeeDefault() {
//        List<Token> amountList = new ArrayList<>();
//        Token amount = new Token();
//        amount.setDenom("okb");
//        amount.setAmount("1.00000000");
//        amountList.add(amount);
//        Fee fee = new Fee();
//        fee.setAmount(amountList);
//        fee.setGas("200000");
//        return fee;
        return null;
    }

    private static Signature sign(byte[] byteSignData, String privateKey) throws Exception {

        //签名
        byte[] sig = Crypto.sign(byteSignData, privateKey);
        //System.out.println(EncodeUtils.bytesToHex(sig));

        String sigResult = Strings.fromByteArray(Base64.encode(sig));
        //System.out.println(sigResult);

        Signature signature = new Signature();
        Pubkey pubkey = new Pubkey();
        pubkey.setType("tendermint/PubKeySecp256k1");
        pubkey.setValue(Strings.fromByteArray(
                Base64.encode(Hex.decode(Crypto.generatePubKeyHexFromPriv(privateKey)))));
        signature.setPubkey(pubkey);
        signature.setSignature(sigResult);

        return signature;
    }


}
