package com.okchain.types;

import java.util.ArrayList;
import java.util.List;


public class Fee {

    private List<Token> amount;

    private String gas;

    public Fee(Token amount, String gas) {
        ArrayList<Token> arrayList = new ArrayList<>();
        arrayList.add(amount);
        this.amount = arrayList;
        this.gas = gas;
    }

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
