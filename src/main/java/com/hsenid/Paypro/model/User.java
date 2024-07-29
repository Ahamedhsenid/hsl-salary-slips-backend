package com.hsenid.Paypro.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Document(collection = "user")
public class User implements UserDetails {

    @Id
    private String id;            // Unique identifier for the user
    private String email;         // User's email address
    private String password;      // User's password (should be encoded)
    private String role;          // User's role, e.g., ADMIN, USER

    // Define user authorities based on the role
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    // Return the user's username, which is the email in this case
    @Override
    public String getUsername() {
        return email;
    }

    // Account status checks
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

