package dev.gavin.devactivitydashboard.model.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class DayCounter {
    @JsonProperty("x")
    private final LocalDate date;
    @JsonProperty("y")
    private final Integer value;

    public DayCounter(LocalDate date, Integer value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getValue() {
        return value;
    }
}
