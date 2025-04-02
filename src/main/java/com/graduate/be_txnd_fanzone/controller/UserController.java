package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.user.CreateUserRequest;
import com.graduate.be_txnd_fanzone.dto.user.CreateUserResponse;
import com.graduate.be_txnd_fanzone.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(@RequestBody CreateUserRequest request) {
        ApiResponse<CreateUserResponse> apiResponse = new ApiResponse<>(userService.createUser(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
