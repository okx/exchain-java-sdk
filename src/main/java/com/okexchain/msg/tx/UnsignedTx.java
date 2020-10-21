package com.okexchain.msg.tx;

import com.okexchain.msg.common.Signature;
import com.okexchain.msg.common.TxValue;
import com.okexchain.msg.common.Data2Sign;

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


    public static BoardcastTx genBroadcastTx(String unsignedTxStr, Signature signature) {
        Data2Sign data = Data2Sign.fromJson(unsignedTxStr);

        TxValue txValue = new TxValue();
        txValue.setMsgs(data.getMsgs());
        txValue.setFee(data.getFee());
        txValue.setMemo(data.getMemo());

        UnsignedTx unsignedTx = new UnsignedTx(txValue, unsignedTxStr);

        return unsignedTx.signed(signature);
    }
}
