package dev.gavin.devactivitydashboard.model.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TimeLength {
    @JsonProperty("id")
    private final Integer id;
    @JsonProperty("time")
    private final Long time;

    public TimeLength(Integer id, Long time) {
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
