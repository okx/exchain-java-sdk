package io.cosmos.msg.utils;

import io.cosmos.types.Signature;

import java.util.ArrayList;
import java.util.List;

public class UnsignedTx {
    private BoardcastTx boardcastTx;
    private String unsignedTxJson;

    public UnsignedTx(BoardcastTx btx, String unsignedTxJson) {
        this.boardcastTx = btx;
        this.unsignedTxJson = unsignedTxJson;
    }

    public BoardcastTx signed(Signature signature) {
        List<Signature> signatureList = new ArrayList<>();
        signatureList.add(signature);
        boardcastTx.getTx().setSignatures(signatureList);
        return boardcastTx;
    }

    public String toString() {
        return unsignedTxJson;
    }
}
