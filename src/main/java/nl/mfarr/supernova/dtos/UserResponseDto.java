package nl.mfarr.supernova.dtos;

public class UserResponseDto {
    private String username;


    public UserResponseDto(String username) {
        this.username = username;
    }

    // Getter
    public String getUsername() {
        return username;
    }
}
