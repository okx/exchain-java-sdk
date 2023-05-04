package com.okbchain.msg.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.env.EnvInstance;
import com.okbchain.msg.common.Token;
import com.okbchain.utils.crypto.AddressUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgSendValue {

    @JsonProperty("amount")
    @SerializedName("amount")
    private List<Token> amount;

    @JsonProperty("from_address")
    @SerializedName("from_address")
    private String fromAddress;

    @JsonProperty("to_address")
    @SerializedName("to_address")
    private String toAddress;

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    public List<Token> getAmount() {
        return amount;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        // eth address
        if (!toAddress.startsWith(EnvInstance.getEnv().GetMainPrefix())) {
            toAddress = AddressUtil.convertAddressFromHexToBech32(toAddress);
        }
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
