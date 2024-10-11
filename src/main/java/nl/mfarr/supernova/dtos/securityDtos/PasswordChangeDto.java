package nl.mfarr.supernova.dtos.securityDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PasswordChangeDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String currentPassword;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setCurrentpassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
