package com.okexchain.msg.ammswap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.msg.common.Token;
import com.okexchain.msg.tx.UnsignedTx;
import com.okexchain.utils.Utils;
import com.okexchain.utils.crypto.PrivateKey;

import java.time.Duration;
import java.time.Instant;

public class MsgAddLiquidity extends MsgBase {

    public MsgAddLiquidity() { setMsgType("okexchain/ammswap/MsgAddLiquidity"); }

    public Message produceMsg(String deadline, String minLiquidity, String maxBaseAmount, String baseDenom, String quoteAmount, String quoteDenom) {

        Token maxBaseToken = new Token();
        maxBaseToken.setDenom(baseDenom);
        maxBaseToken.setAmount(Utils.NewDecString(maxBaseAmount));

        Token quoteToken = new Token();
        quoteToken.setDenom(quoteDenom);
        quoteToken.setAmount(Utils.NewDecString(quoteAmount));

        MsgAddLiquidityValue value = new MsgAddLiquidityValue();

        long current = Instant.now().getEpochSecond() + Duration.parse(deadline).getSeconds();
        value.setDeadline(Long.toString(current));
        value.setSender(this.address);
        value.setMinLiquidity(Utils.NewDecString(minLiquidity));
        value.setMaxBaseAmount(maxBaseToken);
        value.setQuoteAmount(quoteToken);

        Message<MsgAddLiquidityValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchain-66");
        EnvInstance.getEnv().setRestServerUrl("https://okex.com");

        MsgAddLiquidity msg = new MsgAddLiquidity();
        msg.init("okexchain1e4ryfclt57z6d2tgy6rkgvmtf4tyzx8jrqv6x5", "");

        // the lexicographic order of BaseDenom must be less than QuoteDenom
        Message messages = msg.produceMsg(
                "PT24H",
                "1",
                "0.01",
                "btck-ba9",
                "6.5",
                "okt"
        );
        UnsignedTx unsignedTx = msg.getUnsignedTx(messages, "0.05000000", "500000", "");
        JSONObject jsonpObject = JSON.parseObject(unsignedTx.toString(), Feature.OrderedField);
        jsonpObject.put("addressIndex", 81);

        System.out.println(jsonpObject.toString());
    }
}
