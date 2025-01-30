package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;


public class ChangePasswordRequest {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @NotBlank(message = "Old password cannot be empty")
    private String oldPassword;

    @NotBlank(message = "New passowrd cannot be empty")
    private String newPassword;


}
