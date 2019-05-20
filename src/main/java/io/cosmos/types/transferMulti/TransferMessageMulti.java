package io.cosmos.types.transferMulti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.cosmos.types.ValueMulti;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @program: coin-parent-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-22 10:26
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferMessageMulti{

    public TransferMessageMulti() {

    }

    public TransferMessageMulti(String type, ValueMulti value) {
        this.type = type;
        this.value = value;
    }

    private String type;

    private ValueMulti value;

    public String getType() {
        return type;
    }

    public void setValue(ValueMulti value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValueMulti getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("type", type)
            .append("value", value)
            .toString();
    }
}
