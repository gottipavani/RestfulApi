package com.restfulApi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SignUpDto {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Username is required")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;

    public @NotEmpty(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Username is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty(message = "Username is required") String username) {
        this.username = username;
    }

    public @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "Password is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "Password is required") String password) {
        this.password = password;
    }
}
