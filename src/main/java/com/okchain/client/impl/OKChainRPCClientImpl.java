package com.okchain.client.impl;

import com.okchain.common.ConstantIF;
import com.okchain.common.jsonrpc.JSONRPCUtils;
import com.okchain.transaction.BuildTransaction;
import com.okchain.types.AccountInfo;
import com.okchain.types.Token;
import com.okchain.types.TransferUnit;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OKChainRPCClientImpl {
    private String backend;

    private static OKChainRPCClientImpl oKChainRPCClientImpl;

    private OKChainRPCClientImpl(String backend) {
        this.backend = backend;
    }

    public static OKChainRPCClientImpl getOKChainClient(String backend) throws NullPointerException {
        if (oKChainRPCClientImpl == null) {
            oKChainRPCClientImpl = new OKChainRPCClientImpl(backend);
        }
        return oKChainRPCClientImpl;
    }

    public String sendTransaction(byte[] data) {
        String method = "";

        switch (BuildTransaction.getMode()) {
            case ConstantIF.TX_SEND_MODE_BLOCK:
                method = ConstantIF.RPC_METHOD_TX_SEND_BLOCK;
                break;
            case ConstantIF.TX_SEND_MODE_SYNC:
                method = ConstantIF.RPC_METHOD_TX_SEND_SYNC;
                break;
            case ConstantIF.TX_SEND_MODE_ASYNC:
                method = ConstantIF.RPC_METHOD_TX_SEND_ASYNC;
                break;
            default:
                throw new NullPointerException("invalid TX_SEND_MODE");
        }
        Map<String, Object> mp = new TreeMap<>();
        mp.put("tx", data);
        String res = JSONRPCUtils.call(this.backend, method, mp);
        return res;
    }

    // michael.w added 20190715
    public String sendSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws NullPointerException, IOException {
        checkAccountInfoValue(account);
        if (to.equals("")) throw new NullPointerException("Reciever address is empty.");
        if (amount == null || amount.isEmpty()) throw new NullPointerException("Amount is empty.");
        byte[] data = BuildTransaction.generateAminoSendTransaction(account, to, amount, memo);
        return sendTransaction(data);
    }

    public String sendPlaceOrderTransaction(AccountInfo account, String side, String product, String price, String quantity, String memo) throws IOException {
        checkAccountInfoValue(account);
        byte[] data = BuildTransaction.generateAminoPlaceOrderTransaction(account, side, product, price, quantity, memo);
        return sendTransaction(data);
    }

    public String sendCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws IOException{
        checkAccountInfoValue(account);
        byte[] data = BuildTransaction.generateAminoCancelOrderTransaction(account, orderId, memo);
        return sendTransaction(data);
    }

    public String sendMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo)throws IOException {
        checkAccountInfoValue(account);
        byte[] data = BuildTransaction.generateAminoMultiSendTransaction(account, transfers, memo);
        return sendTransaction(data);

    }

    private void checkAccountInfoValue(AccountInfo account) {
        if (account == null) throw new NullPointerException("AccountInfo is empty.");
        if (account.getAccountNumber().equals("")) throw new NullPointerException("AccountNumber is empty.");
        if (account.getSequenceNumber().equals("")) throw new NullPointerException("SequenceNumber is empty.");
        if (account.getPrivateKey().equals("")) throw new NullPointerException("PrivateKey is empty.");
        if (account.getPublicKey().equals("")) throw new NullPointerException("PublicKey is empty.");
        if (account.getUserAddress().equals("")) throw new NullPointerException("UserAddress is empty.");
        //        if (account.getPrivateKey().equals("")) return false;
        //        if (account.getSequenceNumber().equals("") || account.getAccountNumber().equals("")) {
        //            JSONObject accountJson = JSON.parseObject(account.getPrivateKey());
        //            String sequence = getSequance(accountJson);
        //            String accountNumber = getAccountNumber(accountJson);
        //            if (sequence.equals("") || accountNumber.equals("")) return false;
        //            if (account.getSequenceNumber().equals("")) account.setSequenceNumber(sequence);
        //            if (account.getAccountNumber().equals(""))
        // account.setAccountNumber(accountNumber);
        //        }
        //        return true;
        //    }
    }
}
