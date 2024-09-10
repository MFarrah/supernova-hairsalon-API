package nl.mfarr.supernova.dtos;

public class AuthResponseDto {
    private String jwt;

    public AuthResponseDto(String jwt) {
        this.jwt = jwt;
    }


    public String getJwt() {
        return jwt;
    }
}