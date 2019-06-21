package com.okchain.types;

/**
 * @program: coin-parent-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-22 10:29
 **/
public class Token {

    private String amount;

    private String denom;

    public Token() {

    }

    public Token(String amount, String denom) {
        this.amount = amount;
        this.denom = denom;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
