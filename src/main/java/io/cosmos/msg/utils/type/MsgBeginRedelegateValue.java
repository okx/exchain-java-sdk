package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import io.cosmos.types.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgBeginRedelegateValue {
    private Token amount;

    @JsonProperty("delegator_address")
    @SerializedName("delegator_address")
    private String delegatorAddress;

    @JsonProperty("validator_dst_address")
    @SerializedName("validator_dst_address")
    private String validatorDstAddress;

    @JsonProperty("validator_src_address")
    @SerializedName("validator_src_address")
    private String validatorSrcAddress;


    public void setDelegatorAddress(String delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public void setValidatorSrcAddress(String validatorAddress) {
        this.validatorSrcAddress = validatorAddress;
    }

    public void setValidatorDstAddress(String validatorAddress) {
        this.validatorDstAddress = validatorAddress;
    }

    public Token getAmount() {
        return amount;
    }

    public void setAmount(Token amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("delegator_address", delegatorAddress)
                .append("validator_src_address", validatorSrcAddress)
                .append("validator_dst_address", validatorDstAddress)
            .append("amount", amount)
            .toString();
    }
}
