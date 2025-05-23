package com.graduate.be_txnd_fanzone.dto;

import com.graduate.be_txnd_fanzone.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUserDetails implements UserDetails {
    Long userId;
    String username;
    String password;
    String fullName;
    GrantedAuthority authority;

    public CustomUserDetails(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.authority = new SimpleGrantedAuthority("ROLE_"+user.getRole().getRoleName());
    }

    public CustomUserDetails(Long userId, String username, String password, String fullName, GrantedAuthority authority) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

}
