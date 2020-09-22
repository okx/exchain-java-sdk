package io.cosmos.msg.utils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Message<T> {

    public Message() {
    }

    public Message(String type, T value) {
        this.type = type;
        this.value = value;
    }

    private String type;

    private T value;

    public String getType() {
        return type;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getValue() {
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
