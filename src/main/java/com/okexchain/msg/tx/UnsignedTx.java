package com.okexchain.msg.tx;

import com.okexchain.msg.common.Signature;
import com.okexchain.msg.common.TxValue;
import com.okexchain.msg.common.Data2Sign;

import java.util.ArrayList;
import java.util.List;

public class UnsignedTx {
    private BroadcastTx broadcastTx;
    private BroadcastValue broadcastValue;

    private String unsignedTxJson;

    public UnsignedTx(TxValue txValue, String unsignedTxJson) {

        this.broadcastTx = new BroadcastTx();
        this.broadcastTx.setTx(txValue);
        this.broadcastTx.setMode("block");

        this.broadcastValue = new BroadcastValue();
        this.broadcastValue.setTx(txValue);

        this.unsignedTxJson = unsignedTxJson;
    }

    public BroadcastTx signed(Signature signature) {
        List<Signature> signatureList = new ArrayList<>();
        signatureList.add(signature);
        broadcastTx.getTx().setSignatures(signatureList);
        return broadcastTx;
    }

    public BroadcastValue sign4gentx(Signature signature) {
        List<Signature> signatureList = new ArrayList<>();
        signatureList.add(signature);
        broadcastValue.getTx().setSignatures(signatureList);
        return broadcastValue;
    }

    public String toString() {
        return unsignedTxJson;
    }


    public static BroadcastTx genBroadcastTx(String unsignedTxStr, Signature signature) {
        Data2Sign data = Data2Sign.fromJson(unsignedTxStr);

        TxValue txValue = new TxValue();
        txValue.setMsgs(data.getMsgs());
        txValue.setFee(data.getFee());
        txValue.setMemo(data.getMemo());

        UnsignedTx unsignedTx = new UnsignedTx(txValue, unsignedTxStr);

        return unsignedTx.signed(signature);
    }
}
