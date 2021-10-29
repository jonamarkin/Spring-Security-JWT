package io.jamapps.springsecurityjwt.models;

import java.util.ArrayList;

public class UserModel {

    private String userId;
    private String password;
    private String role;

    public UserModel(String userId, String password, String role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    public UserModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
