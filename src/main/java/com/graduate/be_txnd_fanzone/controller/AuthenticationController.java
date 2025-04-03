package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.login.LoginRequest;
import com.graduate.be_txnd_fanzone.dto.login.LoginResponse;
import com.graduate.be_txnd_fanzone.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(authenticationService.authenticate(loginRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
