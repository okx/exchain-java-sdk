package com.okexchain.legacy.types;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Signature {

    @JSONField(name = "pub_key")
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

}
