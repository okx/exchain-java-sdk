package com.okexchain.msg;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.tx.*;
import com.okexchain.utils.HttpUtils;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.Crypto;
import com.okexchain.msg.common.*;
import com.okexchain.utils.crypto.PrivateKey;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;



import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
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

    public void initMnemonic(String mnemonic) {
        String prikeyStr = Crypto.generatePrivateKeyFromMnemonic(mnemonic);
        PrivateKey pri = new PrivateKey(prikeyStr);
        init(pri);
    }

    public void init(PrivateKey privateKey) {
        priKeyString = privateKey.getPriKey();
        init(privateKey.getPubKey());
    }

    private String getAccountPrivate(String userAddress) {
        String url = EnvInstance.getEnv().GetRestServerUrl() +
                EnvInstance.getEnv().GetRestPathPrefix() +
                EnvInstance.getEnv().GetAccountUrlPath() + userAddress;
        System.out.println(url);
        return HttpUtils.httpGet(url);
    }

    private String getSequance(JSONObject account) {
        String res = account
                .getJSONObject("value")
                .get("sequence").toString();
        return res;
    }

    private String getAccountNumber(JSONObject account) {
        String res = account
                .getJSONObject("value")
                .get("account_number").toString();
        return res;
    }

    public static JSONObject broadcast(String tx, String url) {
        System.out.println("Broadcast tx:");
        System.out.println(tx);

        System.out.println("Response:");
        String res = HttpUtils.httpPost(url + EnvInstance.getEnv().GetTxUrlPath(), tx);
        JSONObject result = JSON.parseObject(res);

        System.out.println(result);
        System.out.println("------------------------------------------------------");
        return result;
    }


    public JSONObject submit(Message message,
                             String feeAmount,
                             String gas,
                             String memo) {
        try {
            UnsignedTx unsignedTx = getUnsignedTx(message, Utils.NewDecString(feeAmount), gas, memo);

            Signature signature = MsgBase.signTx(unsignedTx.toString(), priKeyString);

            BroadcastTx signedTx = unsignedTx.signed(signature);

            return broadcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());
        } catch (Exception e) {
            System.out.println("serialize transfer msg failed");
            return new JSONObject();
        }
    }

    public UnsignedTx getUnsignedTx(Message message,
                                    String feeAmount,
                                    String gas,
                                    String memo) {

        UnsignedTx tx = null;
        try {
            // generate unsigned tx
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
            String unsignedTxJson = new ObjectMapper().writeValueAsString(data);

            System.out.println("row data:");
            System.out.println(unsignedTxJson);

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


    public static Signature signTx(String unsignedTx, String privateKey){
        Signature signature=null;
        try {
            byte[] byteSignData = unsignedTx.getBytes();
            BigInteger privKey = new BigInteger(privateKey, 16);
            Sign.SignatureData sig = Sign.signMessage(byteSignData, ECKeyPair.create(privKey));
            String sigResult = toBase64(sig);

            signature = new Signature();
            Pubkey pubkey = new Pubkey();
            pubkey.setType("ethermint/PubKeyEthSecp256k1");
            pubkey.setValue(Strings.fromByteArray(Base64.encode(Hex.decode(Crypto.generatePubKeyHexFromPriv(privateKey)))));
            signature.setPubkey(pubkey);
            signature.setSignature(sigResult);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return signature;
    }


    public void init(String pubkey) {
        pubKeyString = pubkey;
        address = Crypto.generateAddressFromPub(pubKeyString);
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(address));
        sequenceNum = getSequance(accountJson);
        accountNum = getAccountNumber(accountJson);
        operAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
    }

    public void init(String addr, String pubkey) {
        pubKeyString = pubkey;
        address = addr;
        JSONObject accountJson = JSON.parseObject(getAccountPrivate(address));
        sequenceNum = getSequance(accountJson);
        accountNum = getAccountNumber(accountJson);
        operAddress = Crypto.generateValidatorAddressFromPub(pubKeyString);
    }

    public void init(String addr, String accountnum, String sequencenum, String pubkey) {
        pubKeyString = pubkey;
        address = addr;
        accountNum = accountnum;
        sequenceNum = sequencenum;
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
            FileOutputStream fos = new FileOutputStream(path);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(content.getBytes(), 0, content.getBytes().length);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String toBase64(Sign.SignatureData sig) {
        byte[] sigData = new byte[64];  // 32 bytes for R + 32 bytes for S
        System.arraycopy(sig.getR(), 0, sigData, 0, 32);
        System.arraycopy(sig.getS(), 0, sigData, 32, 32);
        System.out.println(Hex.toHexString(sigData));
        return new String(org.spongycastle.util.encoders.Base64.encode(sigData), Charset.forName("UTF-8"));
    }

    public String getMatchedAttribute(JSONObject result, String matchedKey) throws Exception {
        Response response = JSON.parseObject(result.toString(), Response.class);
        if (result.isEmpty()) {
            throw new Exception("result is empty");
        } else if (response.getCode() != 0) {
            throw new Exception("execute tx failed: " + response.getRawLog());
        }

        JSONArray rawLogs = JSON.parseArray(response.getRawLog());
        RawLog<StringEvent<Attribute>> rawLog1 = JSONObject.parseObject(rawLogs.get(0).toString(), new TypeReference<RawLog<StringEvent<Attribute>>>() {});
        for (StringEvent<Attribute> event : rawLog1.getEvents()) {
            for (Attribute attr : event.getAttributes()) {
                if (attr.getKey().equals(matchedKey)) {
                    return attr.getValue();
                }
            }
        }
        throw  new Exception("can not find matched attribute");
    }

    public boolean isTxSucceed(JSONObject result) throws Exception {
        if (result.isEmpty()) {
            throw new Exception("result is empty");
        }

        Response response = JSON.parseObject(result.toString(), Response.class);
        if (response.getRawLog() == null) {
            throw new Exception("result is empty");
        }
        return response.getCode() == 0;
    }

    public String getSequenceNum() {
        return sequenceNum;
    }

    public String getAccountNum() {
        return accountNum;
    }
}
