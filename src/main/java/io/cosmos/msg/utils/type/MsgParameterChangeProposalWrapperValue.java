package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgParameterChangeProposalWrapperValue {

    private MsgParameterChangeProposalValue ParameterChangeProposal;
    private String height;

    public void setProposal(MsgParameterChangeProposalValue proposal) {
        this.ParameterChangeProposal = proposal;
    }

    public void setHeight(String height) {
        this.height = height;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("ParameterChangeProposal", ParameterChangeProposal)
            .append("height", height)
            .toString();
    }
}
