package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IssueType {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("value")
    private final Integer value;

    public IssueType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
