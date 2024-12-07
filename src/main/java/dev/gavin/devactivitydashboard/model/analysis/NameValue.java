package dev.gavin.devactivitydashboard.model.analysis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NameValue {
    @JsonProperty("name")
    private final String name;
    @JsonProperty("value")
    private final Integer value;

    public NameValue(String name, Integer value) {
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
