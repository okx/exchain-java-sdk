package com.okchain.common;

public interface ConstantIF {
    int DECIMAL_N = 8;
    int MAX_MEMO_LEN =256;

    String CHAIN_ID = "okchain";

    String ADDRESS_PREFIX = "okchain";

    String HD_PATH = "M/44H/996H/0H/0/0";

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
