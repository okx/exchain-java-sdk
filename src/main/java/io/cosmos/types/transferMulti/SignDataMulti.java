package io.cosmos.types.transferMulti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.cosmos.types.Fee;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @program: coin-parent-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-19 18:10
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class SignDataMulti {
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("chain_id")
    private String chainId;
    private Fee fee;
    private String memo;
    private TransferMessageMulti[] msgs;
    private String sequence;

    public SignDataMulti() {

    }

    public SignDataMulti(String accountNumber, String chainId, Fee fee, String memo, TransferMessageMulti[] msgs, String sequence) {
        this.accountNumber = accountNumber;
        this.chainId = chainId;
        this.fee = fee;
        this.memo = memo;
        this.msgs = msgs;
        this.sequence = sequence;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public TransferMessageMulti[] getMsgs() {
        return msgs;
    }

    public void setMsgs(TransferMessageMulti[] msgs) {
        this.msgs = msgs;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public void setData(Fee fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("account_number", accountNumber)
            .append("chain_id", chainId)
            .append("fee", fee)
            .append("memo", memo)
            .append("msgs", msgs)
            .append("sequence", sequence)
            .toString();
    }
}
