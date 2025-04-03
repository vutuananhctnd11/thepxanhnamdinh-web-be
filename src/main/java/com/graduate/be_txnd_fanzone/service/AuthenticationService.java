package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.login.LoginRequest;
import com.graduate.be_txnd_fanzone.dto.login.LoginResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    JwtUtil jwtUtil;
    PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new CustomException(ErrorCode.UNAUTHENTICATED);
        }

        String token = jwtUtil.createJwtToken(user);
        return LoginResponse.builder().token(token).authenticated(true).build();
    }

}
