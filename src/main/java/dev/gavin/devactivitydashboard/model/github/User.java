package dev.gavin.devactivitydashboard.model.github;

public class User {
    private final String login;

    public User(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
