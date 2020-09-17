package com.okexchain.types;

public class RequestOrderListOpenParams {
    private String product;
    private String address;
    private String start;
    private String end;
    private String side;
    private String page;
    private String perPage;

    public RequestOrderListOpenParams(String product, String address, String start, String end, String side, String page, String perPage) {
        this.product = product;
        this.address = address;
        this.start = start;
        this.end = end;
        this.side = side;
        this.page = page;
        this.perPage = perPage;
    }

    public RequestOrderListOpenParams(String address) {
        this.address = address;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }
}
