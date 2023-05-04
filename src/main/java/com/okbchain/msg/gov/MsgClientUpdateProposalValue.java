package com.okbchain.msg.gov;


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
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgClientUpdateProposalValue {

    @JsonProperty("title")
    @SerializedName("title")
    private String title;

    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("subject_client_id")
    @SerializedName("subject_client_id")
    private String subjectClientId;

    @JsonProperty("substitute_client_id")
    @SerializedName("substitute_client_id")
    private String substituteClientId;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("title", title)
                .append("description", description)
                .append("subjectClientId", subjectClientId)
                .append("substituteClientId", substituteClientId)
                .toString();
    }

}
