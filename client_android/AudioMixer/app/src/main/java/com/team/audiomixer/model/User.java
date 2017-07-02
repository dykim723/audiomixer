package com.team.audiomixer.model;

/**
 * Created by dykim on 2017-05-28.
 */

public class User {
    private String email = "";
    private String password = "";
    private String nickname = "";

    private String accessToken = "";
    private String refreshToken = "";

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
