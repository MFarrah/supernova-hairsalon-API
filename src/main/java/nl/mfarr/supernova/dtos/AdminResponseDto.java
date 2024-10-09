package nl.mfarr.supernova.dtos;

import nl.mfarr.supernova.enums.Role;

import java.util.Set;

public class AdminResponseDto {
    private Long id;
    private String email;
    private Set<Role> roles;

    // Getters en setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
