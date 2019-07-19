package com.okchain.common;

public interface ConstantIF {
    String CHAIN_ID = "okchain";
    String ADDRESS_PREFIX = "okchain";

    //String HD_PATH = "M/44H/118H/0H/0/0";
    String HD_PATH = "M/44H/996H/0H/0/0";
    //url path
    String ACCOUNT_URL_PATH = "/auth/accounts/";
    String TRANSACTION_URL_PATH = "/txs";

    String GET_ACCOUNT_ALL_TOKENS_URL_PATH = "/accounts/";

    String GET_ACCOUNT_TOKEN_URL_PATH = "/accounts/";

    String GET_TOKENS_URL_PATH = "/tokens";

    String GET_TOKEN_URL_PATH = "/token/";

    String GET_PRODUCTS_URL_PATH = "/products";

    String GET_DEPTHBOOK_URL_PATH = "/order/depthbook";

    String GET_CANDLES_URL_PATH = "/candles/";

    String GET_TICKERS_URL_PATH = "/tickers";

    String GET_ORDERLIST_OPEN_URL_PATH = "/order/list/open";

    String GET_ORDERLIST_CLOSED_URL_PATH = "/order/list/closed";

    String GET_DEALS_URL_PATH = "/deals";

    String GET_TRANSACTIONS_URL_PATH = "/transactions";

    String GET_MATCHES_URL_PATH = "/matches";

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
