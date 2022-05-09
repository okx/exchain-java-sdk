package com.okexchain.msg.ibc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Commitment {
    @JsonProperty("port_id")
    @SerializedName("port_id")
    private String portId;

    @JsonProperty("channel_id")
    @SerializedName("channel_id")
    private String channelId;

    @JsonProperty("sequence")
    @SerializedName("sequence")
    private String sequence;

    @JsonProperty("data")
    @SerializedName("data")
    private String data;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("portId", portId)
                .append("channelId", channelId)
                .append("sequence", sequence)
                .append("data", data)
                .toString();
    }
}
