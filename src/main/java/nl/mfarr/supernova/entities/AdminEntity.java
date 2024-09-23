package nl.mfarr.supernova.entities;

import nl.mfarr.supernova.enums.Role;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "admin_entity")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    private String firstName;
    private String lastName;

    @Column(unique = true)  // Added for unique email
    private String email;

    private String password;  // Password added

    @ElementCollection(fetch = FetchType.LAZY)  // FetchType.LAZY added for better performance
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}