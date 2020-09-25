package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgParamChangeValue {

    @JsonProperty("key")
    @SerializedName("key")
    private String key;

    @JsonProperty("subkey")
    @SerializedName("subkey")
    private String subKey;

    @JsonProperty("subspace")
    @SerializedName("subspace")
    private String subspace;

    @JsonProperty("value")
    @SerializedName("value")
    private String value;

    public void setKey(String key) {
        this.key = key;
    }

    public void setSubspace(String subspace) {
        this.subspace = subspace;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("key", key)
            .append("subkey", subKey)
            .append("subspace", subspace)
            .append("value", value)
            .toString();
    }
}