package hh.backend.goalplanner.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SignUpForm {
    @NotEmpty(message = "Username is required")
    @Size(min=6, max=25, message = "Username length must be between 6 and 25 characters")
    private String username = "";

    @NotEmpty(message = "Password is required")
    @Size(min=8, max=25, message = "Password length must be between 8 and 25 characters")
    private String password = "";

    @NotEmpty(message = "Password check is required")
    @Size(min=8, max=25, message = "Password length must be between 8 and 25 characters")
    private String passwordCheck;

    @NotEmpty
    private String role = "USER";

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

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
