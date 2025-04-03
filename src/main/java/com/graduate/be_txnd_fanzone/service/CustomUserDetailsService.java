package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.CustomUserDetails;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndDeleteFlagIsFalse(username).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }
}
