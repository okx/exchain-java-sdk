package io.okchain.client;


import com.alibaba.fastjson.JSONObject;
import io.okchain.types.AccountInfo;
import io.okchain.types.AddressInfo;
import io.okchain.types.PlaceOrderRequestParms;
import io.okchain.types.Token;

import java.util.List;

public interface OKChainClient {


//    public static OKChainClient getOKChainClient(String backend) {
//        return null;
//    }

    public JSONObject getAccountFromNode(String userAddress) throws NullPointerException;

    public AddressInfo createAddressInfo();

    public AccountInfo getAccountInfo(String privateKey) throws NullPointerException;

    public JSONObject sendSendTransaction(AccountInfo account, String to, List<Token> amount, String memo) throws NullPointerException;


    public JSONObject sendPlaceOrderTransaction(AccountInfo account, PlaceOrderRequestParms parms, String memo) throws NullPointerException;


    public JSONObject sendCancelOrderTransaction(AccountInfo account, String orderId, String memo) throws NullPointerException;


}
