/**
 * Copyright 2022 bejson.com
 */
package com.okexchain.msg.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Block_methods {

    @JsonProperty("sign")
    @SerializedName("sign")
    private String sign;

    @JsonProperty("extra")
    @SerializedName("extra")
    private String extra;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("sign", sign)
                .append("extra", extra)
                .toString();
    }


    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getSign() {
        return sign;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
    public String getExtra() {
        return extra;
    }

}