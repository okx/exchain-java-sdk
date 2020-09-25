package io.cosmos.msg.utils.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import io.cosmos.types.Token;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigInteger;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class MsgListValue {
    @JsonProperty("init_price")
    @SerializedName("init_price")
    private String initPrice;

    @JsonProperty("list_asset")
    @SerializedName("list_asset")
    private String listAsset;

    @JsonProperty("owner")
    @SerializedName("owner")
    private String owner;

    @JsonProperty("quote_asset")
    @SerializedName("quote_asset")
    private String quoteAsset;

    public void setInitPrice(String initPrice) { this.initPrice = initPrice; }

    public void setListAsset(String listAsset) {
        this.listAsset = listAsset;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setQuoteAsset(String quoteAsset) {
            this.quoteAsset = quoteAsset;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("list_asset", listAsset)
                .append("quote_asset", quoteAsset)
                .append("owner", owner)
                .append("init_price", initPrice)
                .toString();
    }
}
