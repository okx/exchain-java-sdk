package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import io.cosmos.types.Signature;
import io.cosmos.types.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgTransferTradingPairOwnershipValue {
    @JsonProperty("from_address")
    @SerializedName("from_address")
    private String fromAddress;

    @JsonProperty("product")
    @SerializedName("product")
    private String product;

    @JsonProperty("to_address")
    @SerializedName("to_address")
    private String toAddress;

    private Signature to_signature;

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setToSignature(Signature to_signature) {
        this.to_signature = to_signature;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("from_address", fromAddress)
                .append("product", product)
                .append("to_address", toAddress)
                .append("to_signature", to_signature)
                .toString();
    }
}
