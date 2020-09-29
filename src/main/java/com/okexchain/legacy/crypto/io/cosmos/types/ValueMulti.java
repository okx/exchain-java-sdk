package com.okexchain.legacy.crypto.io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-22 10:58
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class ValueMulti {

    private List<InputOutput> inputs;
    private List<InputOutput> outputs;

    public List<InputOutput> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputOutput> inputs) {
        this.inputs = inputs;
    }

    public List<InputOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<InputOutput> outputs) {
        this.outputs = outputs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("inputs", inputs)
            .append("outputs", outputs)
            .toString();
    }
}
