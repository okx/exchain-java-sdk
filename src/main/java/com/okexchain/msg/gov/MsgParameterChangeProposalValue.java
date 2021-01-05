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
public class MsgParameterChangeProposalValue  {

    @JsonProperty("changes")
    @SerializedName("changes")
    private JSONArray changes;

    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    public void setChanges(JSONArray changes) {
        this.changes = changes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("title", title)
            .append("description", description)
            .append("changes", changes)
            .toString();
    }
}
