package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MergeTime {
    @JsonProperty("id")
    private final Integer id;
    @JsonProperty("time")
    private final Long time;

    public MergeTime(Integer id, Long time) {
        this.id = id;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public Long getTime() {
        return time;
    }
}
