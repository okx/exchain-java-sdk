package com.okexchain.msg.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okexchain.msg.common.TransferUnits;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgMultiTransferValue {

    private List<TransferUnits> transferUnits;

    @JsonProperty("from")
    @SerializedName("from")
    private String from;

    public List<TransferUnits> getTransferUnits() {
        return transferUnits;
    }

    public void setTransferUnits(List<TransferUnits> transferUnits) { this.transferUnits = transferUnits; }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("from", from)
                .append("transfers", transferUnits)
                .toString();
    }
}
