package io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class CommissionMsg {

    @JsonProperty("max_change_rate")
    @SerializedName("max_change_rate")
    private String maxChangeRate;

    @JsonProperty("max_rate")
    @SerializedName("max_rate")
    private String maxRate;

    private String rate;

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public void setMaxChangeRate(String maxChangeRate) {
        this.maxChangeRate = maxChangeRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("max_change_rate", this.maxChangeRate)
                .append("max_rate", this.maxRate)
                .append("rate", this.rate)
                .toString();
    }
}


