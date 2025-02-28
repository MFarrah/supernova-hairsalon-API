package nl.mfarr.supernova.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private Long id;
    private String password;
    private String userName;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String password, String userName, String[] authorities) {
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.authorities = traslate_roles(authorities);
    }

    public  List<GrantedAuthority> traslate_roles(String[] roles) {
        List<GrantedAuthority> authorities = new ArrayList(roles.length);
        int rolesLength = roles.length;

        for(int i = 0; i < rolesLength; ++i) {
            String role = roles[i];
//            Assert.isTrue(!role.startsWith("ROLE_"), () -> {
//                return role + " cannot start with ROLE_ (it is automatically added)";
//            });
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
