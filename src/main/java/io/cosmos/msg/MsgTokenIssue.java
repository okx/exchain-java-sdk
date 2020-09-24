package io.cosmos.msg;

import io.cosmos.crypto.PrivateKey;
import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgTokenIssueValue;

public class MsgTokenIssue extends MsgBase {

    public MsgTokenIssue () {
        setMsgType("okexchain/token/MsgIssue");
    }

    public static void main(String[] args) {
        PrivateKey key = new PrivateKey("8145bfb1d3acc216c54490952c994d5e3bce09dd65ae73d0c79f892284f721e7");

        MsgTokenIssue msg = new MsgTokenIssue();
        msg.init(key.getAddress(), key.getPubKey());

        Message messages = msg.produceToeknIssueMsg (
                "-description-",
                "FumingCoin",
                "ChangCoin",
                "ChangFumingCoin",
                "10000",
                "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9",
                true);
        msg.submit(messages, "0.01000000", "200000", "okexchain transfer!");
    }

    public Message produceToeknIssueMsg (String description, String symbol, String originalSymbol, String wholeName, String totalSupply, String owner, boolean mintable) {

        MsgTokenIssueValue value = new MsgTokenIssueValue();
        value.setDescription(description);
        value.setSymbol(symbol);
        value.setOriginalSymbol(originalSymbol);
        value.setWholeName(wholeName);
        value.setTotalSupply(totalSupply);
        value.setOwner(owner);
        value.setMintable(mintable);

        Message<MsgTokenIssueValue> msg = new Message<>();
        msg.setType(msgType);
        msg.setValue(value);
        return msg;

    }
}