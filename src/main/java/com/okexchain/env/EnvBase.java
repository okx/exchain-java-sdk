package com.okexchain.env;

import com.okexchain.utils.Utils;

public class EnvBase {

    protected String restServerUrl;
    protected String mainPrefix;
    protected String denom;
    protected String chainID;
    protected String hdPath;
    protected String validatorAddrPrefix;
    protected String pubPrefix;
    protected String restPathPrefix;
    protected String txUrlPath;
    protected String accountUrlPath;

    public EnvBase() {
        this.restServerUrl = "http://127.0.0.1:8545";
        this.mainPrefix = "okexchain";
        this.denom = "okt";
        this.chainID = "okexchain-1";
        this.hdPath = "M/44H/996H/0H/0/0";
        this.validatorAddrPrefix = "okexchainvaloper";
        this.pubPrefix = "okexchainpub";
        this.restPathPrefix = "/okexchain/v1";
        this.txUrlPath = "/okexchain/v1/txs";
        this.accountUrlPath = "/auth/accounts/";
    }

    public String GetMainPrefix() {
        return this.mainPrefix;
    }

    public String GetDenom() {
        return this.denom;
    }

    public String GetChainid() {
        return this.chainID;
    }

    public String GetRestServerUrl() {
        return this.restServerUrl;
    }

    public String GetHDPath() {
        return this.hdPath;
    }

    public String GetValidatorAddrPrefix() {
        return this.validatorAddrPrefix;
    }

    public String GetPubPrefix() {
        return this.pubPrefix;
    }

    public String GetRestPathPrefix() {
        return this.restPathPrefix;
    }

    public String GetTxUrlPath() {
        return this.txUrlPath;
    }

    public String GetAccountUrlPath() {
        return this.accountUrlPath;
    }

    public String GetNode0Mnmonic() {
        return "puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer";
    }

    public String GetNode1Mnmonic() {
        return "palace cube bitter light woman side pave cereal donor bronze twice work";
    }


    public String GetNode0Addr() {
        return "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9";
    }

    public String GetNode1Addr() {
        return "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9";
    }

    public String GetTransferAmount() {
        return Utils.NewDecString("16.00000000");
    }

    public String GetTendermintConsensusPubkey() {
        return "okexchainvalconspub1zcjduepqwfr8lelpqerf8xyc63vqtje0wvhd68h7uce6ludygc28uj5hc9ushev2kp";
    }

    public void setRestServerUrl(String restServerUrl) {
        this.restServerUrl = restServerUrl;
    }

    public void setMainPrefix(String mainPrefix) {
        this.mainPrefix = mainPrefix;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public void setChainID(String chainID) {
        this.chainID = chainID;
    }

    public void setHdPath(String hdPath) {
        this.hdPath = hdPath;
    }

    public void setValidatorAddrPrefix(String validatorAddrPrefix) {
        this.validatorAddrPrefix = validatorAddrPrefix;
    }

    public void setPubPrefix(String pubPrefix) {
        this.pubPrefix = pubPrefix;
    }

    public void setRestPathPrefix(String restPathPrefix) {
        this.restPathPrefix = restPathPrefix;
    }

    public void setTxUrlPath(String txUrlPath) {
        this.txUrlPath = txUrlPath;
    }

    public void setAccountUrlPath(String accountUrlPath) {
        this.accountUrlPath = accountUrlPath;
    }
}
