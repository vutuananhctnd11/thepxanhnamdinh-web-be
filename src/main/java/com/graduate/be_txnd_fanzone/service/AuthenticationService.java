package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.login.LoginRequest;
import com.graduate.be_txnd_fanzone.dto.login.LoginResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    JwtUtil jwtUtil;
    PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByUsernameAndDeleteFlagIsFalse(request.getUsername()).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new CustomException(ErrorCode.PASSWORD_INVALID);
        }

        String accessToken = jwtUtil.createJwtToken(user, false);
        String refreshToken = jwtUtil.createJwtToken(user, true);
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);

        return LoginResponse.builder().token(accessToken).authenticated(true).build();
    }

    public LoginResponse refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null || jwtUtil.isExpiredToken(refreshToken)) {
            throw new CustomException(ErrorCode.UNAUTHENTICATED);
        }

        String username = jwtUtil.getUsernameFromJwtToken(refreshToken);
        User userLogin = userRepository.findByUsernameAndDeleteFlagIsFalse(username).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        String accessToken = jwtUtil.createJwtToken(userLogin, false);

        return LoginResponse.builder().token(accessToken).authenticated(true).build();
    }

}
