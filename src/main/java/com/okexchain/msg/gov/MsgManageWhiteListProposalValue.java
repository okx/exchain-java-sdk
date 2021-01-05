package com.okexchain.msg.gov;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgManageWhiteListProposalValue {

    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("is_added")
    @SerializedName("is_added")
    private boolean isAdded;

    @JsonProperty("pool_name")
    @SerializedName("pool_name")
    private String poolName;

    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("title", title)
                .append("description", description)
                .append("poolName", poolName)
                .append("isAdded", isAdded)
                .toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
