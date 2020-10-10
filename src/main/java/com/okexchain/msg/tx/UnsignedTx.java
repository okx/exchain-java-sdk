package com.okexchain.msg.tx;

import com.okexchain.msg.common.Signature;
import com.okexchain.msg.common.TxValue;

import java.util.ArrayList;
import java.util.List;

public class UnsignedTx {
    private BoardcastTx boardcastTx;
    private BoardcastValue boardcastValue;

    private String unsignedTxJson;

    public UnsignedTx(TxValue txValue, String unsignedTxJson) {

        this.boardcastTx = new BoardcastTx();
        this.boardcastTx.setTx(txValue);
        this.boardcastTx.setMode("block");

        this.boardcastValue = new BoardcastValue();
        this.boardcastValue.setTx(txValue);

        this.unsignedTxJson = unsignedTxJson;
    }

    public BoardcastTx signed(Signature signature) {
        List<Signature> signatureList = new ArrayList<>();
        signatureList.add(signature);
        boardcastTx.getTx().setSignatures(signatureList);
        return boardcastTx;
    }
    public BoardcastValue sign4gentx(Signature signature) {
        List<Signature> signatureList = new ArrayList<>();
        signatureList.add(signature);
        boardcastValue.getTx().setSignatures(signatureList);
        return boardcastValue;
    }

    public String toString() {
        return unsignedTxJson;
    }
}
