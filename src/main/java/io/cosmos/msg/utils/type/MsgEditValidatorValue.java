package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import io.cosmos.types.Description;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgEditValidatorValue {
    @JsonProperty("Description")
    @SerializedName("Description")
    private Description description;

    private String address;

    @JsonProperty("commission_rate")
    @SerializedName("commission_rate")
    private String commissionRate;

    @JsonProperty("min_self_delegation")
    @SerializedName("min_self_delegation")
    private String minSelfDelegation;

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public void setMinSelfDelegation(String minSelfDelegation) {
        this.minSelfDelegation = minSelfDelegation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("address", address)
                .append("min_self_delegation", this.minSelfDelegation)
                .append("Description", this.description)
                .append("commissionRate", this.commissionRate)
                .toString();
    }
}
