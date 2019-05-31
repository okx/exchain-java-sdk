package io.okchain.types;

public class RequestTransactionsParams {
    private String address;
    private String type;
    private String start;
    private String end;
    private String page;
    private String perPage;


    public RequestTransactionsParams(String address, String type, String start, String end, String page, String perPage) {
        this.address = address;
        this.type = type;
        this.start = start;
        this.end = end;
        this.page = page;
        this.perPage = perPage;
    }

    public RequestTransactionsParams(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
