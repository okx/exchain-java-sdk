package com.okexchain.legacy.transaction;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.okexchain.legacy.common.ConstantIF;
import com.okexchain.legacy.crypto.Crypto;
import com.okexchain.legacy.crypto.io.cosmos.util.AddressUtil;
import com.okexchain.legacy.proto.Transfer;
import com.okexchain.legacy.types.*;
import com.okexchain.legacy.types.staking.*;
import com.okexchain.legacy.encoding.EncodeUtils;
import com.okexchain.legacy.encoding.message.AminoEncode;
import com.okexchain.legacy.exception.InvalidFormatException;
import com.okexchain.msg.MsgBase;
import com.okexchain.utils.Utils;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuildTransaction {
    private static String mode = ConstantIF.TX_SEND_MODE_SYNC;
    private static String nativeDenom = ConstantIF.BASE_COIN_NAME;
    private static String defaultAmount = Utils.NewDecString("0.00200000");
    private static String defaultGas = "200000";
    private static Fee fee = new Fee(new Token(defaultAmount, nativeDenom), defaultGas);

    private static String NEW_ORDER_TYPE = "okexchain/order/MsgNew";
    private static String CANCEL_ORDER_TYPE = "okexchain/order/MsgCancel";
    private static String TRANSFER_TYPE = "okexchain/token/MsgTransfer";
    private static String MULTI_TRANSFER_TYPE = "okexchain/token/MsgMultiTransfer";
    private static String CREATE_VALIDATOR_TYPE = "okexchain/staking/MsgCreateValidator";
    private static String EDIT_VALIDATOR_TYPE = "okexchain/staking/MsgEditValidator";
    private static String VOTE_TYPE = "okexchain/staking/MsgVote";


    public static String getMode() {
        return mode;
    }

    public static void setMode(String mode) {
        BuildTransaction.mode = mode;
    }

    public static void setFee(Fee f) {
        fee = f;
    }

    public static Fee getFee() {
        return fee;
    }


    public static byte[] generateAminoMultiPlaceOrderTransaction(AccountInfo account, List<MultiNewOrderItem> items, String memo) throws IOException {
        IMsg msg = new MsgMultiNewOrder(account.getUserAddress(), items);
        IMsg stdMsg = new MsgStd(NEW_ORDER_TYPE, msg);
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
        return buildAminoTransaction(account, msgMultiNewOrderAminoEncoded, stdMsg, memo);

    }


    public static byte[] generateAminoMultiCancelOrderTransaction(AccountInfo account, List<String> orderIdMap, String memo) throws IOException {
        IMsg msg = new MsgMultiCancelOrder(account.getUserAddress(), orderIdMap);
        IMsg stdMsg = new MsgStd(CANCEL_ORDER_TYPE, msg);
        Transfer.MsgMultiCancelOrder.Builder msgMultiCancelOrderBuilder = Transfer.MsgMultiCancelOrder.newBuilder()
                .setSender(ByteString.copyFrom(AddressUtil.decodeAddress(account.getUserAddress())));
        for (String orderId : orderIdMap) {
            msgMultiCancelOrderBuilder.addOrderIdItems(orderId);
        }
        byte[] msgCancelOrderAminoEncoded = AminoEncode.encodeMsgMultiCancelOrder(msgMultiCancelOrderBuilder.build());
        return buildAminoTransaction(account, msgCancelOrderAminoEncoded, stdMsg, memo);
    }


    public static byte[] generateAminoSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws IOException {
        IMsg msg = new MsgSend(account.getUserAddress(), to, amount);
        IMsg stdMsg = new MsgStd(TRANSFER_TYPE, msg);
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
        return buildAminoTransaction(account, msgSendAminoEncoded, stdMsg, memo);
    }


    public static byte[] generateAminoMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) throws IOException {
        IMsg msg = new MsgMultiSend(account.getUserAddress(), transfers);
        IMsg stdMsg = new MsgStd(MULTI_TRANSFER_TYPE, msg);
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
        return buildAminoTransaction(account, msgMultiSendAminoEncoded, stdMsg, memo);
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
        Fee fee = getFee();
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
            // 2.fee
            Transfer.Fee.Builder feeBuilder = Transfer.Fee.newBuilder().setGas(Integer.parseInt(fee.getGas()));
            for (Token t : fee.getAmount()) {
                Transfer.Token tokenProto = Transfer.Token.newBuilder()
                        .setAmount(EncodeUtils.stringTo8(t.getAmount()))
                        .setDenom(t.getDenom()).build();
                feeBuilder.addAmount(tokenProto);
            }
            stdTxBuilder.setFee(feeBuilder.build());
            // 3.signature list
            for (Signature sig : signatures) {
                byte[] pubkeyAminoEncoded = AminoEncode.encodePubkey(account.getPublicKey());
                Transfer.Signature sigProto = Transfer.Signature.newBuilder()
                        .setPubkey(ByteString.copyFrom(pubkeyAminoEncoded))
                        .setSignature(ByteString.copyFrom(Base64.decode(sig.getSignature()))).build();
                stdTxBuilder.addSignatures(sigProto);
            }
            // 4.msg
            Transfer.StdTransaction stdTransactionProto = stdTxBuilder.addMsgs(ByteString.copyFrom(stdMsgProtoBytes)).build();
            return AminoEncode.encodeStdTransaction(stdTransactionProto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static Signature sign(byte[] byteSignData, String privateKey) throws Exception {
        //sign
        com.okexchain.msg.common.Signature signature = MsgBase.signTx(byteSignData.toString(), privateKey);

//        byte[] sig = Crypto.sign(byteSignData, privateKey);
//        String sigResult = Strings.fromByteArray(Base64.encode(sig));
//        Signature signature = new Signature();
//        Pubkey pubkey = new Pubkey();
//        pubkey.setType("ethermint/PubKeyEthSecp256k1");
//        pubkey.setValue(Strings.fromByteArray(
//                Base64.encode(Hex.decode(Crypto.generatePubKeyHexFromPriv(privateKey)))));
//        signature.setPubkey(pubkey);
//        signature.setSignature(sigResult);
        Signature newSig = new Signature();
        newSig.setPubkey(signature.getPubkey());
        newSig.setSignature(signature.getSignature());
        return newSig;
    }


    public static String generatePlaceOrdersTransaction(AccountInfo account, List<MultiNewOrderItem> items, String memo) {
        IMsg msg = new MsgMultiNewOrder(account.getUserAddress(), items);
        IMsg stdMsg = new MsgStd(NEW_ORDER_TYPE, msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }


    public static String generateCancelOrdersTransaction(AccountInfo account, List<String> orderIdMap, String memo) {
        IMsg msg = new MsgMultiCancelOrder(account.getUserAddress(), orderIdMap);
        IMsg stdMsg = new MsgStd(CANCEL_ORDER_TYPE, msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }

    public static String generateSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) {
        IMsg msg = new MsgSend(account.getUserAddress(), to, amount);
        IMsg stdMsg = new MsgStd(TRANSFER_TYPE, msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }

    public static String generateMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) {
        IMsg msg = new MsgMultiSend(account.getUserAddress(), transfers);
        IMsg stdMsg = new MsgStd(MULTI_TRANSFER_TYPE, msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }

    public static String generateCreateValidatorTransaction(AccountInfo account, Description description, CommissionRates commission, Token minSelfDelegation,
                                                            String delegatorAddress, String validatorAddress, String pubKey, String memo) {
        IMsg msg = new MsgCreateValidator(description, commission, minSelfDelegation,
                delegatorAddress, validatorAddress, pubKey);
        IMsg stdMsg = new MsgStd(CREATE_VALIDATOR_TYPE, msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }

    public static String generateEditValidatorTransaction(AccountInfo account, String minSelfDelegation,  String validatorAddress, Description description, String memo) {
        IMsg msg = new MsgEditValidator(validatorAddress, description, minSelfDelegation);
        IMsg stdMsg = new MsgStd(EDIT_VALIDATOR_TYPE, msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }

    public static String generateVoteTransaction(AccountInfo account, String delegatorAddress, String[] validatorAddresses, String memo) {
        IMsg msg = new MsgVote(delegatorAddress, validatorAddresses);
        IMsg stdMsg = new MsgStd(VOTE_TYPE, msg);
        return buildTransaction(account, stdMsg, stdMsg, memo);
    }

    private static String buildTransaction(AccountInfo account, IMsg stdMsg, IMsg signMsg, String memo) {
        if (account.getAccountNumber() == "" || account.getSequenceNumber() == "") {

        }
        if (memo == null) {
            memo = "";
        }

        Fee fee = getFee();
        SignData signData = new SignData(account.getAccountNumber(), ConstantIF.CHAIN_ID, fee, memo, new IMsg[]{signMsg}, account.getSequenceNumber());
        try {
            String signDataJson = JSONObject.toJSONString(signData);


            System.out.println("signData: " + signDataJson);
            Signature signature = sign(signDataJson.getBytes(), account.getPrivateKey());
            List<Signature> signatures = new ArrayList<>();
            signatures.add(signature);
            StdTransaction stdTransaction = new StdTransaction(new IMsg[]{stdMsg}, fee, signatures, memo);

            PostTransaction postTransaction = new PostTransaction(stdTransaction, mode);
            return JSON.toJSONString(postTransaction);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
