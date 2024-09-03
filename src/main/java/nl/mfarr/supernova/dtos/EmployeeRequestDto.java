package nl.mfarr.supernova.dtos;

public class EmployeeRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;  // Wordt alleen bij het aanmaken gebruikt
    private String role;      // "ROLE_USER", "ROLE_EMPLOYEE", "ROLE_ADMIN"

    public EmployeeRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
