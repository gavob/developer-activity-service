package dev.gavin.devactivitydashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Contributor {
    @JsonProperty("id")
    private final Integer id;
    @JsonProperty("login")
    private final String login;
    @JsonProperty("avatar_url")
    private final String avatarUrl;
    @JsonProperty("contributions")
    private final Integer contributions;

    public Contributor(Integer id, String login, String avatarUrl, Integer contributions) {
        this.id = id;
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.contributions = contributions;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Integer getContributions() {
        return contributions;
    }
}
