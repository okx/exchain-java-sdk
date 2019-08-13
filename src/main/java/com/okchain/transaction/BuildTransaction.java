package com.okchain.transaction;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.okchain.common.ConstantIF;
import com.okchain.crypto.AddressUtil;
import com.okchain.crypto.Crypto;
import com.okchain.encoding.EncodeUtils;
import com.okchain.encoding.message.AminoEncode;
import com.okchain.proto.Transfer;
import com.okchain.types.*;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuildTransaction {
    private static String mode = ConstantIF.TX_SEND_MODE_SYNC;

    public static String getMode() {
        return mode;
    }

    public static void setMode(String mode) {
        BuildTransaction.mode = mode;
    }

    public static String generatePlaceOrderTransaction(AccountInfo account, String side, String product, String price, String quantity, String memo) {

        IMsg msg = new MsgNewOrder(price, product, quantity, account.getUserAddress(), side);
        IMsg stdMsg = new MsgStd("order/new", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    public static byte[] generateAminoPlaceOrderTransaction(AccountInfo account, String side, String product, String price, String quantity, String memo) throws IOException {
        IMsg msg = new MsgNewOrder(price, product, quantity, account.getUserAddress(), side);
        // stdMsg to Proto and to ProtoBytes
        // first 2 get the protobytes of object MsgNewOrder
        Transfer.MsgNewOrder msgNewOrderProto = Transfer.MsgNewOrder.newBuilder()
                .setBatchNumber("")
                .setDepth("")
                .setPrice(EncodeUtils.stringTo8(price))
                .setProduct(product)
                .setQuantity(EncodeUtils.stringTo8(quantity))
                .setSender(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress())))
                .setSide(side).build();

        System.out.println(msgNewOrderProto);
        byte[] msgNewOrderAminoEncoded = AminoEncode.encodeMsgNewOrder(msgNewOrderProto);
        return buildAminoTransaction(account, msgNewOrderAminoEncoded, msg, memo);

    }

    public static String generateCancelOrderTransaction(AccountInfo account, String orderId, String memo) {
        IMsg msg = new MsgCancelOrder(account.getUserAddress(), orderId);
        IMsg stdMsg = new MsgStd("order/cancel", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    // michael.w added 20190710
    public static byte[] generateAminoCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws IOException {
        IMsg msg = new MsgCancelOrder(account.getUserAddress(), orderId);
        Transfer.MsgCancelOrder msgCancelOrderProto = Transfer.MsgCancelOrder.newBuilder()
                .setOrderId(orderId)
                .setSender(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress()))).build();
        byte[] msgCancelOrderAminoEncoded = AminoEncode.encodeMsgCancelOrder(msgCancelOrderProto);
        return buildAminoTransaction(account, msgCancelOrderAminoEncoded, msg, memo);
    }

    public static String generateSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) {
        // 从谁到谁 转多钱(List)
        IMsg msg = new MsgSend(account.getUserAddress(), to, amount);
        IMsg stdMsg = new MsgStd("token/Send", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    // michael.w added 20190710
    public static byte[] generateAminoSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws IOException {
        IMsg msg = new MsgSend(account.getUserAddress(), to, amount);
        // stdMsg to Proto and to ProtoBytes
        // first 2 get the protobytes of object MsgSend
        Transfer.MsgSend.Builder msgSendBuilder = Transfer.MsgSend.newBuilder()
                .setFromAddress(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress())))
                .setToAddress(ByteString.copyFrom(AddressUtil.decodeAddress(to)));
        for (Token t : amount) {
            Transfer.Token tokenProto = Transfer.Token.newBuilder().setAmount(EncodeUtils.stringTo8(t.getAmount())).setDenom(t.getDenom()).build();
            msgSendBuilder.addAmount(tokenProto);
        }

        // then 2 get the protobytes of object stdMsg
        byte[] msgSendAminoEncoded = AminoEncode.encodeMsgSend(msgSendBuilder.build());
        return buildAminoTransaction(account, msgSendAminoEncoded, msg, memo);
    }

    // michael.w added 20190708
    public static String generateSendTransactions(AccountInfo account, List<String> tos, List<List<Token>> amounts, String memo) {
        if (tos.size() != amounts.size()) {
            return "the lengths of receiver addresses ,values and memos are not the same";
        }
        List<IMsg> msgs = new ArrayList<>();
        List<IMsg> stdMsgs = new ArrayList<>();
        for (int i = 0; i < tos.size(); i++) {
            IMsg msg = new MsgSend(account.getUserAddress(), tos.get(i), amounts.get(i));
            IMsg stdMsg = new MsgStd("token/Send", msg);
            msgs.add(msg);
            stdMsgs.add(stdMsg);
        }
        return buildTransactions(account, stdMsgs, msgs, memo);


    }

    public static String generateMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) {
        IMsg msg = new MsgMultiSend(account.getUserAddress(), transfers);
        IMsg stdMsg = new MsgStd("token/MultiSend", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    // michael.w added 20190710
    public static byte[] generateAminoMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) throws IOException {
        IMsg msg = new MsgMultiSend(account.getUserAddress(), transfers);
        Transfer.MsgMultiSend.Builder msgMultiSendBuilder = Transfer.MsgMultiSend.newBuilder().setFrom(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress())));
        for (TransferUnit tu : transfers) {
            Transfer.TransferUnit.Builder transferUnitBuilder = Transfer.TransferUnit.newBuilder().setTo(ByteString.copyFrom(AddressUtil.decodeAddress(tu.getTo())));
            for (Token t : tu.getCoins()) {
                Transfer.Token tokenProto = Transfer.Token.newBuilder()
                        .setAmount(EncodeUtils.stringTo8(t.getAmount()))
                        .setDenom(t.getDenom()).build();
                transferUnitBuilder.addCoins(tokenProto);
            }
            msgMultiSendBuilder.addTransfers(transferUnitBuilder.build());
        }

        byte[] msgMultiSendAminoEncoded = AminoEncode.encodeMsgMultiSend(msgMultiSendBuilder.build());
        return buildAminoTransaction(account, msgMultiSendAminoEncoded, msg, memo);
    }

    private static String buildTransaction(AccountInfo account, IMsg stdMsg, IMsg signMsg, String memo) {
        if (account.getAccountNumber() == "" || account.getSequenceNumber() == "") {

        }
        if (memo == null) {
            memo = "";
        }
        // 暂无手续费
        Fee fee = generateFeeDefault();
        // 需要对account中的accountNumber和sequenceNumber、chain_id、手续费、memo、IMsg实现类集合签名
        SignData signData = new SignData(account.getAccountNumber(), ConstantIF.CHAIN_ID, fee, memo, new IMsg[]{signMsg}, account.getSequenceNumber());
        try {
            // signData转为Json串
            String signDataJson = JSONObject.toJSONString(signData);

            // 对signData的Json串利用私钥签名

            System.out.println("signData: " + signDataJson);
            Signature signature = sign(signDataJson.getBytes(), account.getPrivateKey());
            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);
            StdTransaction stdTransaction = new StdTransaction(new IMsg[]{stdMsg}, fee, signatures, memo);
            //组装待广播交易结构

            PostTransaction postTransaction = new PostTransaction(stdTransaction, mode);
            return JSON.toJSONString(postTransaction);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // michael.w added 20190709
    private static byte[] buildAminoTransaction(AccountInfo account, byte[] stdMsgProtoBytes, IMsg signMsg, String memo) {
        if (account.getAccountNumber() == "" || account.getSequenceNumber() == "") {
            throw new NullPointerException("account error!");
        }
        if (memo == null) {
            memo = "";
        }

        //Fee fee = generateFeeDefault();
        // 需要对account中的accountNumber和sequenceNumber、chain_id、手续费、memo、IMsg实现类集合签名
        SignData signData = new SignData(account.getAccountNumber(), ConstantIF.CHAIN_ID, null, memo, new IMsg[]{signMsg}, account.getSequenceNumber());
        try {
            // signData转为Json串
            String signDataJson = JSONObject.toJSONString(signData);
            // 对signData的Json串利用私钥签名
            Signature signature = sign(signDataJson.getBytes(), account.getPrivateKey());
            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);

            // 拼装StdTransaction的protobuf
            Transfer.StdTransaction.Builder stdTxBuilder = Transfer.StdTransaction.newBuilder();
            // 1.拼装成员memo(fee已经被剔除)
            stdTxBuilder.setMemo(memo);
            // 2.拼装成员signatures
            for (Signature sig : signatures) {
                byte[] pubkeyAminoEncoded = AminoEncode.encodePubkey(account.getPublicKey());
                Transfer.Signature sigProto = Transfer.Signature.newBuilder()
                        .setPubkey(ByteString.copyFrom(pubkeyAminoEncoded))
                        .setSignature(ByteString.copyFrom(Base64.decode(sig.getSignature()))).build();
                stdTxBuilder.addSignatures(sigProto);
            }
            // 3.拼装msgs(目前一个tx中只有包含一个msg)
            Transfer.StdTransaction stdTransactionProto = stdTxBuilder.addMsgs(ByteString.copyFrom(stdMsgProtoBytes)).build();
            return AminoEncode.encodeStdTransaction(stdTransactionProto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String buildTransactions(AccountInfo account, List<IMsg> stdMsgs, List<IMsg> signMsgs, String memo) {
        if (stdMsgs.size() != signMsgs.size()) {
            return "the lengths of stdMsgs ,signMsgs are not the same. ";
        }
        Fee fee = generateFeeDefault();
        SignData signData = new SignData(account.getAccountNumber(), ConstantIF.CHAIN_ID, fee, memo, new IMsg[]{signMsgs.get(0), signMsgs.get(1)}, account.getSequenceNumber());
        try {
            // signData转为Json串
            String signDataJson = JSONObject.toJSONString(signData);
            // 对signData的Json串利用私钥签名
            Signature signature = sign(signDataJson.getBytes(), account.getPrivateKey());
            //组装签名结构
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);
//            System.out.println(stdMsgs.get(0).toString());
//            System.out.println(stdMsgs.get(1).toString());

            StdTransaction stdTransaction = new StdTransaction(new IMsg[]{stdMsgs.get(0), stdMsgs.get(1)}, fee, signatures, memo);
            //组装待广播交易结构

            PostTransaction postTransaction = new PostTransaction(stdTransaction, "block");
//            System.out.println(JSON.toJSONString(postTransaction));
            return JSON.toJSONString(postTransaction);

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
