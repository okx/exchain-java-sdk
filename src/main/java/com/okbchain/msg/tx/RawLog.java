package com.okbchain.msg.tx;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RawLog<T> {

    public int getIndex() {
        return index;
    }

    public List<T> getEvents() {
        return events;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setEvents(List<T> events) {
        this.events = events;
    }

    @JsonProperty("msg_index")
    @SerializedName("msg_index")
    private int index;

    @JsonProperty("events")
    @SerializedName("events")
    private List<T> events;
}
