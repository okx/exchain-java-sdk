package com.okchain.transaction;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.okchain.common.ConstantIF;
import com.okchain.crypto.Crypto;
import com.okchain.crypto.io.cosmos.util.AddressUtil;
import com.okchain.encoding.EncodeUtils;
import com.okchain.encoding.message.AminoEncode;
import com.okchain.exception.InvalidFormatException;
import com.okchain.proto.Transfer;
import com.okchain.types.*;
import com.okchain.types.staking.CommissionRates;
import com.okchain.types.staking.Description;
import com.okchain.types.staking.MsgCreateValidator;
import com.okchain.types.staking.MsgEditValidator;
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



    public static byte[] generateAminoMultiPlaceOrderTransaction(AccountInfo account, List<MultiNewOrderItem> items, String memo) throws IOException {
        IMsg msg = new MsgMultiNewOrder(account.getUserAddress(), items);
        // stdMsg to Proto and to ProtoBytes
        // first 2 get the protobytes of object MsgNewOrder
        Transfer.MsgMultiNewOrder.Builder msgMultiNewOrderBuilder = Transfer.MsgMultiNewOrder.newBuilder();
        for (MultiNewOrderItem item : items) {
            Transfer.MultiNewOrderItem.Builder itemBuilder = Transfer.MultiNewOrderItem.newBuilder();
            itemBuilder.setSide(item.getSide());
            itemBuilder.setProduct(item.getProduct());
            itemBuilder.setPrice(EncodeUtils.stringTo8(item.getPrice()));
            itemBuilder.setQuantity(EncodeUtils.stringTo8(item.getQuantity()));
            msgMultiNewOrderBuilder.addOrderItems(itemBuilder.build());
        }
        msgMultiNewOrderBuilder.setSender(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress())));
        byte[] msgMultiNewOrderAminoEncoded = AminoEncode.encodeMsgMultiNewOrder(msgMultiNewOrderBuilder.build());
        return buildAminoTransaction(account, msgMultiNewOrderAminoEncoded, msg, memo);

    }


    public static byte[] generateAminoMultiCancelOrderTransaction(AccountInfo account, List<String> orderIdMap, String memo) throws IOException {
        IMsg msg = new MsgMultiCancelOrder(account.getUserAddress(), orderIdMap);
        Transfer.MsgMultiCancelOrder.Builder msgMultiCancelOrderBuilder = Transfer.MsgMultiCancelOrder.newBuilder()
                .setSender(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress())));
        for (String orderId : orderIdMap) {
            msgMultiCancelOrderBuilder.addOrderIdItems(orderId);
        }
        byte[] msgCancelOrderAminoEncoded = AminoEncode.encodeMsgMultiCancelOrder(msgMultiCancelOrderBuilder.build());
        return buildAminoTransaction(account, msgCancelOrderAminoEncoded, msg, memo);
    }


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



    private static byte[] buildAminoTransaction(AccountInfo account, byte[] stdMsgProtoBytes, IMsg signMsg, String memo) {
        if (account.getAccountNumber().equals("") || account.getSequenceNumber().equals("")) {
            throw new NullPointerException("account error!");
        }
        if (memo == null) {
            memo = "";
        }
        if (memo.length()>ConstantIF.MAX_MEMO_LEN) throw new InvalidFormatException("length of memo is too long");
        // no fee temporarily
        Fee fee = generateFeeDefault();
        // prepare for sign
        SignData signData = new SignData(account.getAccountNumber(), ConstantIF.CHAIN_ID, fee, memo, new IMsg[]{signMsg}, account.getSequenceNumber());
        System.out.println("signData:" + JSON.toJSONString(signData));
        try {
            // object 2 json string
            String signDataJson = JSONObject.toJSONString(signData);
            // sign the json string with private key
            Signature signature = sign(signDataJson.getBytes(), account.getPrivateKey());
            //  fill the signature list
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);

            // build StdTransaction protobuf
            Transfer.StdTransaction.Builder stdTxBuilder = Transfer.StdTransaction.newBuilder();
            // 1.memo
            stdTxBuilder.setMemo(memo);
            // 2.signature list
            for (Signature sig : signatures) {
                byte[] pubkeyAminoEncoded = AminoEncode.encodePubkey(account.getPublicKey());
                Transfer.Signature sigProto = Transfer.Signature.newBuilder()
                        .setPubkey(ByteString.copyFrom(pubkeyAminoEncoded))
                        .setSignature(ByteString.copyFrom(Base64.decode(sig.getSignature()))).build();
                stdTxBuilder.addSignatures(sigProto);
            }
            // 3.msg
            Transfer.StdTransaction stdTransactionProto = stdTxBuilder.addMsgs(ByteString.copyFrom(stdMsgProtoBytes)).build();
            return AminoEncode.encodeStdTransaction(stdTransactionProto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Fee generateFeeDefault() {
        return null;
    }

    private static Signature sign(byte[] byteSignData, String privateKey) throws Exception {
        //sign
        byte[] sig = Crypto.sign(byteSignData, privateKey);
        String sigResult = Strings.fromByteArray(Base64.encode(sig));
        Signature signature = new Signature();
        Pubkey pubkey = new Pubkey();
        pubkey.setType("tendermint/PubKeySecp256k1");
        pubkey.setValue(Strings.fromByteArray(
                Base64.encode(Hex.decode(Crypto.generatePubKeyHexFromPriv(privateKey)))));
        signature.setPubkey(pubkey);
        signature.setSignature(sigResult);

        return signature;
    }


    public static String generatePlaceOrdersTransaction(AccountInfo account, List<MultiNewOrderItem> items, String memo) {
        IMsg msg = new MsgMultiNewOrder(account.getUserAddress(), items);
        IMsg stdMsg = new MsgStd("okchain/order/MsgNew", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }


    public static String generateCancelOrdersTransaction(AccountInfo account, List<String> orderIdMap, String memo) {
        IMsg msg = new MsgMultiCancelOrder(account.getUserAddress(), orderIdMap);
        IMsg stdMsg = new MsgStd("okchain/order/MsgCancel", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    public static String generateSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) {
        IMsg msg = new MsgSend(account.getUserAddress(), to, amount);
        IMsg stdMsg = new MsgStd("okchain/token/MsgTransfer", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    public static String generateMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) {
        IMsg msg = new MsgMultiSend(account.getUserAddress(), transfers);
        IMsg stdMsg = new MsgStd("okchain/token/MsgMultiTransfer", msg);
        return buildTransaction(account, stdMsg, msg, memo);
    }

    public static String generateCreateValidatorTransaction(AccountInfo account, Description description, CommissionRates commission, Token minSelfDelegation,
                                                            String delegatorAddress, String validatorAddress, String pubKey, String memo) {
        IMsg msg = new MsgCreateValidator(description, commission, minSelfDelegation,
                delegatorAddress, validatorAddress, pubKey);
        IMsg stdMsg = new MsgStd("okchain/staking/MsgCreateValidator", msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }

    public static String generateEditValidatorTransaction(AccountInfo account, String minSelfDelegation,  String validatorAddress, Description description, String memo) {
        IMsg msg = new MsgEditValidator(validatorAddress, description, minSelfDelegation);
        IMsg stdMsg = new MsgStd("okchain/staking/MsgEditValidator", msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
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


}
