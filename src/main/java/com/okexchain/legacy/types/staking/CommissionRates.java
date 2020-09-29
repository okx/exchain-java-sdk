package com.okexchain.legacy.types.staking;

import com.alibaba.fastjson.annotation.JSONField;

public class CommissionRates {
    @JSONField(name = "max_change_rate")
    private String maxChangeRate;// maximum daily increase of the validator commission, as a fraction

    @JSONField(name = "max_rate")
    private String maxRate;//  maximum commission rate which validator can ever charge, as a fraction

    private String rate;//  the commission rate charged to delegators, as a fraction

    public CommissionRates(String rate, String maxRate, String maxChangeRate) {
        this.rate = rate;
        this.maxRate = maxRate;
        this.maxChangeRate = maxChangeRate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public String getMaxChangeRate() {
        return maxChangeRate;
    }

    public void setMaxChangeRate(String maxChangeRate) {
        this.maxChangeRate = maxChangeRate;
    }
}
