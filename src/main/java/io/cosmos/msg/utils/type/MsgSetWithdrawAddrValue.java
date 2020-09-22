package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgSetWithdrawAddrValue {

    @JsonProperty("delegator_address")
    @SerializedName("delegator_address")
    private String delegatorAddress;

    @JsonProperty("withdraw_address")
    @SerializedName("withdraw_address")
    private String withdrawAddress;

    public void setWithdrawAddress(String address) {
        this.withdrawAddress = address;
    }

    public void setDelegatorAddress(String address) {
        this.delegatorAddress = address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("delegator_address", delegatorAddress)
            .append("withdraw_address", withdrawAddress)
            .toString();
    }
}
