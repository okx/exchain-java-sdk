package com.okexchain.msg.dex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgWithdrawDepositValue {

    private Token amount;

    @JsonProperty("depositor")
    @SerializedName("depositor")
    private String depositor;

    @JsonProperty("product")
    @SerializedName("product")
    private String product;

    public void setAmount(Token amount) {
        this.amount = amount;
    }

    public Token getAmount() {
        return amount;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("product", product)
                .append("depositor", depositor)
                .append("amount", amount)
                .toString();
    }
}
