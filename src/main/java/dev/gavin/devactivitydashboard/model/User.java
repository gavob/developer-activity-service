package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("id")
    private final Integer id;
    @JsonProperty("login")
    private final String login;

    public User(Integer id, String login) {
        this.id = id;
        this.login = login;
    }
}
