package com.okchain.types;

import java.util.List;

public class TransferUnit {
    private List<Token> coins;
    private String to;

    public TransferUnit(List<Token> coins, String to) {
        this.coins = coins;
        this.to = to;
    }

    public List<Token> getCoins() {
        return coins;
    }

    public void setCoins(List<Token> coins) {
        this.coins = coins;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
