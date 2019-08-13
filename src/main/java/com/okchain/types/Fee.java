package com.okchain.types;

import java.util.List;


public class Fee {

    private List<Token> amount;

    private String gas;

    public List<Token> getAmount() {
        return amount;
    }

    public String getGas() {
        return gas;
    }

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

}
