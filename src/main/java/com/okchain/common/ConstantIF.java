package com.okchain.common;

public interface ConstantIF {
    String CHAIN_ID = "okchain";
    String ADDRESS_PREFIX = "okchain";

    String HD_PATH = "M/44H/996H/0H/0/0";
    //url path
    String REST_PATH_PREFIX = "/okchain/v1";

    String ACCOUNT_URL_PATH = REST_PATH_PREFIX + "/auth/accounts/";
    String TRANSACTION_URL_PATH = REST_PATH_PREFIX + "/txs";

    String GET_ACCOUNT_ALL_TOKENS_URL_PATH = REST_PATH_PREFIX + "/accounts/";

    String GET_ACCOUNT_TOKEN_URL_PATH = REST_PATH_PREFIX + "/accounts/";

    String GET_TOKENS_URL_PATH = REST_PATH_PREFIX + "/tokens";

    String GET_TOKEN_URL_PATH = REST_PATH_PREFIX + "/token/";

    String GET_PRODUCTS_URL_PATH = REST_PATH_PREFIX + "/products";

    String GET_DEPTHBOOK_URL_PATH = REST_PATH_PREFIX + "/order/depthbook";

    String GET_CANDLES_URL_PATH = REST_PATH_PREFIX + "/candles/";

    String GET_TICKERS_URL_PATH = REST_PATH_PREFIX + "/tickers";

    String GET_ORDERLIST_OPEN_URL_PATH = REST_PATH_PREFIX + "/order/list/open";

    String GET_ORDERLIST_CLOSED_URL_PATH = REST_PATH_PREFIX + "/order/list/closed";

    String GET_DEALS_URL_PATH = REST_PATH_PREFIX + "/deals";

    String GET_TRANSACTIONS_URL_PATH = REST_PATH_PREFIX + "/transactions";

    String GET_MATCHES_URL_PATH = REST_PATH_PREFIX + "/matches";

    //RPC transaction send mode
    String TX_SEND_MODE_BLOCK = "block";

    String TX_SEND_MODE_SYNC = "sync";

    String TX_SEND_MODE_ASYNC = "async";

    //RPC method
    String RPC_METHOD_TX_SEND_BLOCK = "broadcast_tx_commit";

    String RPC_METHOD_TX_SEND_SYNC = "broadcast_tx_sync";

    String RPC_METHOD_TX_SEND_ASYNC = "broadcast_tx_async";

    String RPC_METHOD_QUERY = "abci_query";


}
