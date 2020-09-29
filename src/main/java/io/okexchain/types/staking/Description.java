package io.okexchain.types.staking;

public class Description {
    private String details;  // optional details
    private String identity;// optional identity signature (ex. UPort or Keybase)
    private String moniker;//  string `json:"moniker" yaml:"moniker"`   // name
    private String website; // optional website link


    public Description(String moniker, String identity, String website, String details) {
        this.moniker = moniker;
        this.identity = identity;
        this.website = website;
        this.details = details;
    }

    public String getMoniker() {
        return moniker;
    }

    public void setMoniker(String moniker) {
        this.moniker = moniker;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
