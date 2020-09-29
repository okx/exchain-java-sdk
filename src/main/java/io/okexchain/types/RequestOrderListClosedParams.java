package io.okexchain.types;

public class RequestOrderListClosedParams extends RequestOrderListOpenParams {
    private String hideNoFill;

    public RequestOrderListClosedParams(String product, String address, String start, String end, String side, String page, String perPage, String hideNoFill) {
        super(product, address, start, end, side, page, perPage);
        this.hideNoFill = hideNoFill;
    }

    public RequestOrderListClosedParams(String address) {
        super(address);
    }

    public String getHideNoFill() {
        return hideNoFill;
    }

    public void setHideNoFill(String hideNoFill) {
        this.hideNoFill = hideNoFill;
    }
}
