package com.okchain.client;


import com.alibaba.fastjson.JSONObject;
import com.okchain.crypto.keystore.CipherException;
import com.okchain.types.*;
import com.okchain.types.staking.CommissionRates;
import com.okchain.types.staking.Description;

import java.io.IOException;
import java.util.List;

public interface OKChainClient {


    // account

    public AccountInfo createAccount();

    public JSONObject getAccountFromNode(String userAddress) throws NullPointerException;

    public AddressInfo getAddressInfo(String privateKey) throws NullPointerException;

    public String getPrivateKeyFromMnemonic(String mnemonic);

    public String generateMnemonic();

    public AccountInfo getAccountInfoFromMnemonic(String mnemo);

    public String generateKeyStore(String privateKey, String passWord) throws CipherException, IOException;

    public String getPrivateKeyFromKeyStore(String keyStoreFilePath, String passWord) throws IOException, CipherException;
    // transact

    public JSONObject sendSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws NullPointerException, IOException;

    public JSONObject sendPlaceOrderTransaction(AccountInfo account, RequestPlaceOrderParams params, String memo) throws NullPointerException, IOException;

    public JSONObject sendCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws NullPointerException, IOException;

    public JSONObject sendMultiSendTransaction(AccountInfo account, List<TransferUnit> transfers, String memo) throws IOException;

    //query

    public AccountInfo getAccountInfo(String privateKey) throws NullPointerException;

    public BaseModel getAccountALLTokens(String address, String show) throws NullPointerException;

    public BaseModel getAccountToken(String address, String symbol) throws NullPointerException;

    public BaseModel getTokens();

    public BaseModel getToken(String symbol) throws NullPointerException;

    public BaseModel getProducts();

    public BaseModel getDepthBook(String product) throws NullPointerException;

    public BaseModel getCandles(String granularity, String instrumentId, String size) throws NullPointerException;

    public BaseModel getTickers(String count);

    public BaseModel getMatches(String product, String start, String end, String page, String perPage) throws NullPointerException;

    public BaseModel getOrderListOpen(RequestOrderListOpenParams params) throws NullPointerException;

    public BaseModel getOrderListClosed(RequestOrderListClosedParams params) throws NullPointerException;

    public BaseModel getDeals(RequestDealsParams params) throws NullPointerException;

    public BaseModel getTransactions(RequestTransactionsParams params) throws NullPointerException;

    public String getTickersV2(String instrumentId);

    public String getInstrumentsV2();

    public String getOrderListOpenV2(String instrument_id, String after, String before, int limit) throws NullPointerException;

    public String getOrderV2(String order_id);

    public JSONObject sendPlaceOrderTransactionV2(AccountInfo account, RequestPlaceOrderParams parms, String memo) throws NullPointerException, IOException;

    public JSONObject sendCancelOrderTransactionV2(AccountInfo account, String orderId, String memo) throws NullPointerException, IOException;

    public JSONObject sendCreateValidatorTransaction(AccountInfo account, Description description, CommissionRates commission, Token minSelfDelegation,
                                                     String delegatorAddress, String validatorAddress, String pubKey, String memo) throws NullPointerException;
    public JSONObject sendEditValidatorTransaction(AccountInfo account, String minSelfDelegation,  String validatorAddress, Description description, String memo) throws NullPointerException;

    public JSONObject sendDepositTransaction(AccountInfo account, String delegatorAddress, Token amount,  String memo) throws NullPointerException;

    public JSONObject sendAddSharesTransaction(AccountInfo account, String delegatorAddress, String[] validatorAddress, String memo) throws NullPointerException;
    }
