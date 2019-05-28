package io.okchain.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.okchain.types.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class MsgSend implements IMsg {

    private List<Token> amount;

    @JsonProperty("from_address")
    @SerializedName("from_address")
    private String fromAddress;

    @JsonProperty("to_address")
    @SerializedName("to_address")
    private String toAddress;

    public MsgSend() {
    }

    public MsgSend(String fromAddress, String toAddress, List<Token> amount) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
    }

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    public List<Token> getAmount() {
        return amount;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("from_address", fromAddress)
                .append("to_address", toAddress)
                .append("amount", amount)
                .toString();
    }
}
