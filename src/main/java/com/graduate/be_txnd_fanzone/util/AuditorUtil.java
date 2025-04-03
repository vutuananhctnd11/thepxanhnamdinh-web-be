package com.graduate.be_txnd_fanzone.util;

import com.graduate.be_txnd_fanzone.dto.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorUtil implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) return Optional.empty();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Optional.of(userDetails.getUsername());
    }
}
