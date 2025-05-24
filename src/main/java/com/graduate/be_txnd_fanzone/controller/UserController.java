package com.graduate.be_txnd_fanzone.controller;

import com.cloudinary.Api;
import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.search.SearchRequest;
import com.graduate.be_txnd_fanzone.dto.search.SearchUserResponse;
import com.graduate.be_txnd_fanzone.dto.user.*;
import com.graduate.be_txnd_fanzone.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request, HttpServletResponse httpResponse) {
        userService.forgotPassword(request.getIdentifier(), httpResponse);
        return new ResponseEntity<>(new ApiResponse<>(null), HttpStatus.OK);
    }

    @GetMapping("/admin/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getAdminLoginInfo () {
        ApiResponse<UserInfoResponse> apiResponse = new ApiResponse<>(userService.getUserLoginInfo());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/personal-page")
    public ResponseEntity<ApiResponse<PersonalPageResponse>> getUserInfo (@RequestParam Long userId) {
        ApiResponse<PersonalPageResponse> apiResponse = new ApiResponse<>(userService.getPersonalPage(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<PageableListResponse<SearchUserResponse>>> searchUsers (@RequestBody SearchRequest request) {
        ApiResponse<PageableListResponse<SearchUserResponse>> apiResponse = new ApiResponse<>(userService.searchUsers(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/list-by-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PageableListResponse<UserShortInfoResponse>>> getListUserByRole (@RequestParam int roleId,
                                                                                                       @RequestParam int page,
                                                                                                       @RequestParam int limit) {
        ApiResponse<PageableListResponse<UserShortInfoResponse>> apiResponse = new ApiResponse<>(
                userService.getListUserByRole(roleId, page, limit));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<PageableListResponse<UserShortInfoResponse>>> getListUser (@RequestParam int page,
                                                                                                 @RequestParam int limit) {
        ApiResponse<PageableListResponse<UserShortInfoResponse>> apiResponse = new ApiResponse<>(
                userService.getListUser(page, limit));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserManagementResponse>> getUserManagementInfo (@RequestParam Long userId) {
        ApiResponse<UserManagementResponse> apiResponse = new ApiResponse<>(userService.getUserManagementInfo(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<UserShortInfoResponse>> createUserByAdmin (@RequestBody AdminCreateUserRequest request) {
        ApiResponse<UserShortInfoResponse> apiResponse = new ApiResponse<>(userService.createUserByAdmin(request));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
