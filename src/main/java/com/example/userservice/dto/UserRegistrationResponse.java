package com.example.userservice.dto;

public class UserRegistrationResponse {
    private UserDTO user;
    private String token;

    //Constructors
    public UserRegistrationResponse() {
    }

    public UserRegistrationResponse(UserDTO user, String token) {
        this.user = user;
        this.token = token;

    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
