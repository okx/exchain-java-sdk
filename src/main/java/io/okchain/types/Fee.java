package io.okchain.types;

import java.util.List;

/**
 * @program: coin-parent-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-19 18:51
 **/

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
