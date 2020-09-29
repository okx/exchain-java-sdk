package io.cosmos.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.cosmos.common.EnvInstance;
import io.cosmos.common.HttpUtils;
import io.cosmos.common.Utils;
import io.cosmos.crypto.Crypto;
import io.cosmos.msg.utils.*;
import io.cosmos.types.Fee;
import io.cosmos.types.Pubkey;
import io.cosmos.types.Signature;
import io.cosmos.types.Token;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.List;

public class MsgBase {


    protected String sequenceNum;
    protected String accountNum;
    protected String pubKeyString;
    protected String address;
    protected String operAddress;
    protected String priKeyString;

    static protected String msgType;

    public void setMsgType(String type) {
        this.msgType = type;
    }

    void initMnemonic(String mnemonic) {
        String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
        init(prikey);
    }

    void init(String privateKey) {
        pubKeyString = Hex.toHexString(Crypto.generatePubKeyFromPriv(privateKey));
        address = Crypto.generateAddressFromPriv(privateKey);
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(address));
        sequenceNum = getSequance(accountJson);
        accountNum = getAccountNumber(accountJson);
        priKeyString = privateKey;

        operAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
    }

    void init(String privateKey, String accountNum, String sequenceNum) {
        pubKeyString = Hex.toHexString(Crypto.generatePubKeyFromPriv(privateKey));
        address = Crypto.generateAddressFromPriv(privateKey);
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(address));
        this.sequenceNum = sequenceNum;
        this.accountNum = accountNum;
        priKeyString = privateKey;

        operAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
    }

    private String getAccountPrivate(String userAddress) {
        String url = EnvInstance.getEnv().GetRestServerUrl() +
                EnvInstance.getEnv().GetRestPathPrefix() +
                EnvInstance.getEnv().GetAccountUrlPath() + userAddress;
        System.out.println(url);
        return HttpUtils.httpGet(url);
    }

    private String getSequance(JSONObject account) {
        String res = (String) account
//                .getJSONObject("result")
                .getJSONObject("value")
                .get("sequence");
        return res;
    }

    private String getAccountNumber(JSONObject account) {
        String res = (String) account
//                .getJSONObject("result")
                .getJSONObject("value")
                .get("account_number");
        return res;
    }

    public static JSONObject boardcast(String tx, String url) {
        System.out.println("Boardcast tx:");
        System.out.println(tx);

        System.out.println("Response:");
        String res = HttpUtils.httpPost(url + EnvInstance.getEnv().GetTxUrlPath(), tx);
        JSONObject result = JSON.parseObject(res);

        System.out.println(result);
        System.out.println("------------------------------------------------------");
        return result;
    }



    public void submit(Message message,
                         String feeAmount,
                         String gas,
                         String memo) {
        try {
            UnsignedTx unsignedTx = getUnsignedTx(message, feeAmount, gas, memo);

            Signature signature = MsgBase.signTx(unsignedTx.toString(), priKeyString);

            BoardcastTx signedTx = unsignedTx.signed(signature);

            boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());
        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }
    }

    public UnsignedTx getUnsignedTx(Message message,
                                    String feeAmount,
                                    String gas,
                                    String memo) {

        UnsignedTx tx = null;
        try {
            List<Token> amountList = new ArrayList<>();
            Token amount = new Token();
            amount.setDenom(EnvInstance.getEnv().GetDenom());
            amount.setAmount(feeAmount);
            amountList.add(amount);

            //组装待签名交易结构
            Fee fee = new Fee();
            fee.setAmount(amountList);
            fee.setGas(gas);

            Message[] msgs = new Message[1];
            msgs[0] = message;

            Data2Sign data = new Data2Sign(accountNum, EnvInstance.getEnv().GetChainid(), fee, memo, msgs, sequenceNum);
            String unsignedTxJson = Utils.serializer.toJson(data);

            System.out.println("row data:");
            System.out.println(data);
            System.out.println("json data:");
            System.out.println(unsignedTxJson);

            BoardcastTx cosmosTransaction = new BoardcastTx();
            cosmosTransaction.setMode("block");

            TxValue cosmosTx = new TxValue();
            cosmosTx.setType("auth/StdTx");
            cosmosTx.setMsgs(msgs);

            cosmosTx.setFee(fee);

            cosmosTx.setMemo(memo);

            cosmosTransaction.setTx(cosmosTx);

            tx = new UnsignedTx(cosmosTransaction, unsignedTxJson);
        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }

        return tx;
    }


    public static Signature signTx(String unsignedTx, String privateKey) throws Exception {

        byte[] byteSignData = unsignedTx.getBytes();
        System.out.println("byte data length:");
        System.out.println(byteSignData.length);

        byte[] sig = Crypto.sign(byteSignData, privateKey);
        String sigResult = Strings.fromByteArray(Base64.encode(sig));

        Signature signature = new Signature();
        Pubkey pubkey = new Pubkey();
        pubkey.setType("tendermint/PubKeySecp256k1");
        pubkey.setValue(Strings.fromByteArray(Base64.encode(Hex.decode(Crypto.generatePubKeyHexFromPriv(privateKey)))));
        signature.setPubkey(pubkey);
        signature.setSignature(sigResult);

        System.out.println("privateKey: ");
        System.out.println(privateKey);

        System.out.println("signature: ");
        System.out.println(sigResult);

        return signature;
    }


    public void init(String addr, String pubkey) {
        pubKeyString = pubkey;
        address = addr;
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(address));
        sequenceNum = getSequance(accountJson);
        accountNum = getAccountNumber(accountJson);
        operAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
    }
}
