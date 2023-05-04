package com.okbchain.msg.gov;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.okbchain.msg.common.Treasures;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgContractManageTreasuresProposalValue {

    @JsonProperty("title")
    @SerializedName("title")
    private String title;


    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("treasures")
    @SerializedName("treasures")
    private List<Treasures> treasures;


    @JsonProperty("is_added")
    @SerializedName("is_added")
    private boolean isAdded;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("title", title)
                .append("description", description)
                .append("treasures", treasures)
                .append("isAdded", isAdded)
                .toString();
    }
}
