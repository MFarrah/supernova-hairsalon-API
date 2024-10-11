package nl.mfarr.supernova.dtos.adminDtos;

public class AdminRequestDto {
    private String email;
    private String password;  // Wachtwoord toegevoegd



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
