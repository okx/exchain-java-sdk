package io.cosmos.msg;

import io.cosmos.msg.utils.Message;
import io.cosmos.msg.utils.type.MsgTokenIssueValue;

public class MsgTokenIssue extends MsgBase {

    public MsgTokenIssue () {
        setMsgType("okexchain/token/MsgIssue");
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