package com.okexchain.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.okexchain.env.EnvInstance;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.Crypto;
import com.okexchain.msg.common.*;
import com.okexchain.msg.tx.BoardcastTx;
import com.okexchain.msg.tx.UnsignedTx;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
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
//        String prikey = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
//        init(prikey);
    }

    void init(String privateKey) {
//        pubKeyString = Hex.toHexString(Crypto.generatePubKeyFromPriv(privateKey));
//        address = Crypto.generateAddressFromPriv(privateKey);
//        JSONObject accountJson = JSON.parseObject(getAccountPrivate(address));
//        sequenceNum = getSequance(accountJson);
//        accountNum = getAccountNumber(accountJson);
//        priKeyString = privateKey;
//
//        operAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
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
            UnsignedTx unsignedTx = getUnsignedTx(message, Utils.NewDecString(feeAmount), gas, memo);

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
            //组装待签名交易结构
            Fee fee = new Fee();
            List<Token> amountList = new ArrayList<>();
            fee.setAmount(amountList);

            if (feeAmount.length() > 0) {
                Token amount = new Token();
                amount.setDenom(EnvInstance.getEnv().GetDenom());
                amount.setAmount(Utils.NewDecString(feeAmount));
                amountList.add(amount);
            }
            fee.setGas(gas);

            Message[] msgs = new Message[1];
            msgs[0] = message;

            Data2Sign data = new Data2Sign(accountNum, EnvInstance.getEnv().GetChainid(), fee, memo, msgs, sequenceNum);
            String unsignedTxJson = Utils.serializer.toJson(data);

            System.out.println("row data:");
            System.out.println(data);
//            System.out.println("json data to sign:");
//            System.out.println(unsignedTxJson);

            TxValue txValue = new TxValue();
            txValue.setMsgs(msgs);
            txValue.setFee(fee);
            txValue.setMemo(memo);

            tx = new UnsignedTx(txValue, unsignedTxJson);
        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
        }

        return tx;
    }


    public static Signature signTx(String unsignedTx, String privateKey) throws Exception {
        System.out.println("data to sign:");
        System.out.println(unsignedTx);

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

    public void init(String pubkey, String accountNum, String sequenceNum) {
        pubKeyString = pubkey;
        address = Crypto.generateAddressFromPub(pubKeyString);
        this.sequenceNum = sequenceNum;
        this.accountNum = accountNum;
        operAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
    }


    public void persist(String path, String content) {
        try {
            FileOutputStream fos=new FileOutputStream(path);
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            bos.write(content.getBytes(),0,content.getBytes().length);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
