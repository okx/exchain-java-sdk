package io.okchain.api.transaction;


import io.cosmos.crypto.Crypto;
import io.cosmos.types.Fee;
import io.cosmos.types.Pubkey;
import io.cosmos.types.Signature;
import io.cosmos.types.Token;
import io.cosmos.util.EncodeUtils;
import io.okchain.client.OKChainClient;
import io.okchain.http.OKChainRequest;
import io.okchain.types.*;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.List;

public class BuildTransaction {
    public static String generatePlaceOrderTransaction(OKChainClient okc, String side, String product, String price, String quantity, String memo, String sequence) {
        IMsg msg = new MsgNewOrder("", "", price, product, quantity, okc.userAddress, side);
        IMsg stdMsg = new MsgStd("order/new", msg);
        return buildTransaction(okc, stdMsg, msg, sequence, memo);
    }

    public static String generatePlaceOrderTransaction(OKChainClient okc, String side, String product, String price, String quantity, String memo) {
        return generatePlaceOrderTransaction(okc, side, product, price, quantity, memo, "");
    }

    public static String generateCancelOrderTransaction(OKChainClient okc, String orderId, String memo, String sequence) {
        IMsg msg = new MsgCancelOrder(okc.userAddress, orderId);
        IMsg stdMsg = new MsgStd("order/cancel", msg);
        return buildTransaction(okc, stdMsg, msg, sequence, memo);
    }

    public static String generateCancelOrderTransaction(OKChainClient okc, String orderId, String memo) {
        return generateCancelOrderTransaction(okc, orderId, memo, "");
    }

    public static String generateSendTransaction(OKChainClient okc, String to, List<Token> amount, String memo, String sequence) {
        IMsg msg = new MsgSend(okc.userAddress, to, amount);
        IMsg stdMsg = new MsgStd("token/Send", msg);
        return buildTransaction(okc, stdMsg, msg, sequence, memo);
    }

    public static String generateSendTransaction(OKChainClient okc, String to, List<Token> amount, String memo) {
        return generateSendTransaction(okc, to, amount, memo, "");
    }

    private static String buildTransaction(OKChainClient okc, IMsg stdMsg, IMsg signMsg, String sequence, String memo) {
        if (sequence == "") {
            sequence = OKChainRequest.GetSequance(okc);
        }
        Fee fee = generateFeeDefault();
        SignData signData = new SignData(okc.accountNumber, okc.chainId, fee, memo, new IMsg[]{signMsg}, sequence);
        try {
            byte[] byteSignData = EncodeUtils.toJsonEncodeBytes(signData);
            System.out.println(new String(byteSignData));
            Signature signature = sign(byteSignData, okc.privateKey);
            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);
            StdTransaction stdTransaction = new StdTransaction(new IMsg[]{stdMsg}, fee, signatures, memo);
            //组装待广播交易结构

            PostTransaction postTransaction = new PostTransaction(stdTransaction, "block");
            return postTransaction.toJson();

        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
            return "";
        }


    }

    private static Fee generateFeeDefault() {
        List<Token> amountList = new ArrayList<>();
        Token amount = new Token();
        amount.setDenom("okb");
        amount.setAmount("1.00000000");
        amountList.add(amount);
        Fee fee = new Fee();
        fee.setAmount(amountList);
        fee.setGas("200000");
        return fee;
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
