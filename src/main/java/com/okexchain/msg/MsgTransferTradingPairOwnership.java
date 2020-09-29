package com.okexchain.msg;

import com.okexchain.msg.common.Message;
import com.okexchain.msg.types.MsgTransferTradingPairOwnershipValue;
import com.okexchain.msg.common.Pubkey;
import com.okexchain.msg.common.Signature;

public class MsgTransferTradingPairOwnership extends MsgBase{
    public MsgTransferTradingPairOwnership() {
        setMsgType("okexchain/dex/MsgTransferTradingPairOwnership");
    }

    public static void main(String[] args) throws Exception {
        MsgTransferTradingPairOwnership msg = new MsgTransferTradingPairOwnership();

        msg.initMnemonic("puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer");

/*        EnvInstance.setEnv(new LocalEnv("http://localhost:26659"));

        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        msg.init(key.getAddress(), key.getPubKey());

        Message unsigMessages = msg.produceTransferTradingPairOwnershipMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "okexchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptddw77gw",
                "eos-654_okt"
        );
        PrivateKey toKey = new PrivateKey("29892b64003fc5c8c89dc795a2ae82aa84353bb4352f28707c2ed32aa1011884");

        UnsignedTx unsignedToTx = msg.getUnsignedTx(unsigMessages,"", "200000", "");

        Signature toSignature = MsgBase.signTx(unsignedToTx.toString(), toKey.getPriKey());

        Message messagesUN = msg.produceTransferTradingPairOwnershipMsg(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "okexchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptddw77gw",
                "eos-654_okt"
                toSignature
        );

        UnsignedTx unsignedTx = msg.getUnsignedTx(messages,"0.01000000", "200000", "");

        Signature signature = MsgBase.signTx(unsignedTx.toString(), key.getPriKey());

        BoardcastTx signedTx = unsignedTx.signed(signature);

        MsgBase.boardcast(signedTx.toJson(), EnvInstance.getEnv().GetRestServerUrl());
*/

        Message messages = msg.produceAddSign(
                "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9",
                "okexchain1g7c3nvac7mjgn2m9mqllgat8wwd3aptddw77gw",
                "eos-654_okt"
        );

        msg.submit(messages, "0.00200000", "200000", "");
    }

    public Message produceAddSign(String from, String to, String product) {

        Signature toSignature = new Signature();
//        toSignature.setSignature(signature.getSignature());
        toSignature.setSignature("q/Nf1YotMQ6VIFnP9itnh+sCY6G299K8gZH6UEuHoE4fu1oGiPShJHUJHLv7q8vrbrXjDaLc/qDasWAeb0IaGw==");
        Pubkey p = new Pubkey();
//        p.setValue(signature.getPubkey().getValue());
//        p.setType(signature.getPubkey().getType());
        p.setType("tendermint/PubKeySecp256k1");
        p.setValue("Aga5P7TWoqq+6cmxOTHhj9tLqFlHNlLpWMEdAfHcokp+");
        toSignature.setPubkey(p);

        MsgTransferTradingPairOwnershipValue value = new MsgTransferTradingPairOwnershipValue();
        value.setToSignature(toSignature);
        value.setFromAddress(from);
        value.setToAddress(to);
        value.setProduct(product);

        Message<MsgTransferTradingPairOwnershipValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;
    }
}
