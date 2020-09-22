package io.cosmos.common;

public class OKEnv extends EnvBase {

    public String GetMainPrefix(){
        return "okchain";
    };
    public String GetDenom(){
        return "okt";
    }
    public String GetChainid(){
        return "okchain";
    }

    public String GetRestServerUrl(){
        return  "http://localhost:26659";
    }

    public String GetHDPath(){
        return "M/44H/996H/0H/0/0";
    }

    public String GetValidatorAddrPrefix(){
        return  "okchainvaloper";
    }

    public String GetRestPathPrefix(){
        return  "/okchain/v1";
    }

    public String GetTxUrlPath() {
        return "/okchain/v1/txs";
    };

    public boolean HasFee() {
        return false;
    };


    public String GetNode0Mnmonic() {
        return "puzzle glide follow cruel say burst deliver wild tragic galaxy lumber one";
    };
    public String GetNode1Mnmonic() {
        return "palace cube bitter light woman side pave cereal donor bronze twice work";
    };

    public String GetNode0Addr() {
        return "okchain10q0rk5qnyag7wfvvt7rtphlw589m7frsmyq4ya";
    };
    public String GetNode1Addr() {
        return "okchain1v853tq96n9ghvyxlvqyxyj97589clccr33yr7a";
    };


    public String GetTransferAmount() {
        return "16.00000000";
    };


    public String GetTendermintConsensusPubkey() {
        return "okchainvalconspub1zcjduepqwfr8lelpqerf8xyc63vqtje0wvhd68h7uce6ludygc28uj5hc9ushev2kp";
    };

}
