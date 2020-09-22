package io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Signature {

    @JsonProperty("pub_key")
    @SerializedName("pub_key")
    private Pubkey pubkey;

    private String signature;

    public Pubkey getPubkey() {
        return pubkey;
    }

    public String getSignature() {
        return signature;
    }

    public void setPubkey(Pubkey pubkey) {
        this.pubkey = pubkey;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("pub_key", pubkey)
            .append("signature", signature)
            .toString();
    }
}
