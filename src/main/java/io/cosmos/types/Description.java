package io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Description {

    // very important, ensure order: d i m w
    private String details;
    private String identity;

    private String moniker;
    private String website;
//    private String website;
//    private String moniker;

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("details", this.details)
                .append("identity", this.identity)
                .append("website", this.website)
                .append("moniker", this.moniker)
                .toString();
    }
}
