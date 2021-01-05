package com.okexchain.msg.dex;

import com.okexchain.env.EnvInstance;
import com.okexchain.msg.MsgBase;
import com.okexchain.msg.common.Message;
import com.okexchain.utils.crypto.PrivateKey;

public class MsgCreateOperator extends MsgBase {
    public MsgCreateOperator() {
        setMsgType("okexchain/dex/CreateOperator");
    }

    public static void main(String[] args) {
        EnvInstance.getEnv().setChainID("okexchainevm-8");
        EnvInstance.getEnv().setRestServerUrl("http://localhost:8545");

        PrivateKey key = new PrivateKey("EA6D97F31E4B70663594DD6AFC3E3550AAB5FDD9C44305E8F8F2003023B27FDA");
        MsgCreateOperator msg = new MsgCreateOperator();

        msg.init(key);

        Message messages = msg.produceCreateOperatorMsg(
                key.getAddress(),
                "https://captain.okg/operator.json");

        // okexchaincli tx dex register-operator --website http://captain.okg.com/operator.json --from captain --fees 0.02okt -b block -y
        msg.submit(messages, "0.05000000", "500000", "okexchain dex create operator!");
    }

    public Message produceCreateOperatorMsg(String handlingFeeAddress, String website) {

        MsgCreateOperatorValue value = new MsgCreateOperatorValue();
        value.setOwner(this.address);
        value.setHandlingFeeAddress(handlingFeeAddress);
        value.setWebsite(website);

        Message<MsgCreateOperatorValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}