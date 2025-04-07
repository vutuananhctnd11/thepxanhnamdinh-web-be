package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.user.*;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateUserResponse>> createUser(@RequestBody CreateUserRequest request) {
        ApiResponse<CreateUserResponse> apiResponse = new ApiResponse<>(userService.createUser(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UpdateUserResponse>> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        ApiResponse<UpdateUserResponse> apiResponse = new ApiResponse<>(userService.updateUser(userId, request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> softDeleteUser(@PathVariable Long userId) {
        userService.softDeleteUser(userId);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserLoginInfo () {
        ApiResponse<UserInfoResponse> apiResponse = new ApiResponse<>(userService.getUserLoginInfo());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK); 
    }
}
