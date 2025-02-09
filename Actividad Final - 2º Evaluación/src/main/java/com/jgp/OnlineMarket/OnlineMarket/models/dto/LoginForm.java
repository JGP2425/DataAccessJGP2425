package com.jgp.OnlineMarket.OnlineMarket.models.dto;

import javax.validation.constraints.NotBlank;

public class LoginForm {
    @NotBlank(message="Username (CIF) cannot be empty")
    private String username;
    @NotBlank(message="Password cannot be empty")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
