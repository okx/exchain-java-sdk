package com.okchain.transaction;


import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.okchain.common.ConstantIF;
import com.okchain.crypto.Crypto;
import com.okchain.crypto.io.cosmos.util.AddressUtil;
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


    public static byte[] generateAminoPlaceOrderTransaction(AccountInfo account, String side, String product, String price, String quantity, String memo) throws IOException {
        IMsg msg = new MsgNewOrder(price, product, quantity, account.getUserAddress(), side);
        // stdMsg to Proto and to ProtoBytes
        // first 2 get the protobytes of object MsgNewOrder
        Transfer.MsgNewOrder msgNewOrderProto = Transfer.MsgNewOrder.newBuilder()
                .setPrice(EncodeUtils.stringTo8(price))
                .setProduct(product)
                .setQuantity(EncodeUtils.stringTo8(quantity))
                .setSender(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress())))
                .setSide(side).build();

        byte[] msgNewOrderAminoEncoded = AminoEncode.encodeMsgNewOrder(msgNewOrderProto);
        return buildAminoTransaction(account, msgNewOrderAminoEncoded, msg, memo);

    }


    public static byte[] generateAminoCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws IOException {
        IMsg msg = new MsgCancelOrder(account.getUserAddress(), orderId);
        Transfer.MsgCancelOrder msgCancelOrderProto = Transfer.MsgCancelOrder.newBuilder()
                .setOrderId(orderId)
                .setSender(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress()))).build();
        byte[] msgCancelOrderAminoEncoded = AminoEncode.encodeMsgCancelOrder(msgCancelOrderProto);
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
        if (account.getAccountNumber() == "" || account.getSequenceNumber() == "") {
            throw new NullPointerException("account error!");
        }
        if (memo == null) {
            memo = "";
        }
        // no fee temporarily
        Fee fee = generateFeeDefault();
        // prepare for sign
        SignData signData = new SignData(account.getAccountNumber(), ConstantIF.CHAIN_ID, fee, memo, new IMsg[]{signMsg}, account.getSequenceNumber());
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


}
