package com.example.userservice.dto;

public class UserRegistrationResponse {
    private String token;
    private String message;

    public UserRegistrationResponse() {
    }

    public UserRegistrationResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
