package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class DateFrequency {
    @JsonProperty("y")
    private final Integer value;
    @JsonProperty("x")
    private final LocalDate date;

    public DateFrequency(LocalDate date, Integer value) {
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
