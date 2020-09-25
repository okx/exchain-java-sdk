package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import io.cosmos.msg.utils.Message;
import io.cosmos.types.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgSubmitDeListProposalValue {

    private Message<MsgDeListProposalValue> content;

    @JsonProperty("initial_deposit")
    @SerializedName("initial_deposit")
    private List<Token> initialDeposit;

    private String proposer;

    public void setContent(Message<MsgDeListProposalValue> content) {
        this.content = content;
    }

    public void setInitialDeposit(List<Token> initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("content", content)
            .append("initial_deposit", initialDeposit)
            .append("proposer", proposer)
            .toString();
    }
}
