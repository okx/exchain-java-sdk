package io.cosmos.common;

import io.cosmos.crypto.Crypto;

public class EnvBase {

    public String GetMainPrefix(){
        return "okexchain";
    };
    public String GetDenom(){
        return "okt";
    }
    public String GetChainid(){
        return "okexchain";
    }

    public String GetRestServerUrl(){
        return  "http://localhost:26659";
    }

    public String GetHDPath(){
        return "M/44H/996H/0H/0/0";
    }

    public String GetValidatorAddrPrefix(){
        return  "okexchainvaloper";
    }

    public String GetRestPathPrefix(){
        return  "/okexchain/v1";
    }

    public String GetTxUrlPath() {
        return "/okexchain/v1/txs";
    };

    public boolean HasFee() {
        return true;
    };


    public String GetNode0Mnmonic() {
        return "puzzle glide follow cruel say burst deliver wild tragic galaxy lumber offer";
    };
    public String GetNode1Mnmonic() {
        return "palace cube bitter light woman side pave cereal donor bronze twice work";
    };


    public String GetNode0Addr() {
        return "okexchain10q0rk5qnyag7wfvvt7rtphlw589m7frsku8qc9";
    };
    public String GetNode1Addr() {
        return "okexchain1v853tq96n9ghvyxlvqyxyj97589clccrufrkz9";
    };


    public String GetTransferAmount() {
        return "16.00000000";
    };


    public String GetTendermintConsensusPubkey() {
        return "okexchainvalconspub1zcjduepqwfr8lelpqerf8xyc63vqtje0wvhd68h7uce6ludygc28uj5hc9ushev2kp";
    };
}
