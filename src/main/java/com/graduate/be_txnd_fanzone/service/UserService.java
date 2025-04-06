package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.user.*;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.enums.RoleEnums;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.UserMapper;
import com.graduate.be_txnd_fanzone.model.Role;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.RoleRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) throw new CustomException(ErrorCode.USER_EXISTED);
        if (userRepository.existsByEmailAddress(request.getEmailAddress())) throw new CustomException(ErrorCode.EMAIL_EXISTED);
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByRoleName(RoleEnums.USER.name()).orElseThrow(() ->
                new CustomException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(role);
        userRepository.save(user);
        CreateUserResponse response = userMapper.toCreateUserResponse(user);
        response.setRole(user.getRole().getRoleName());
        return response;
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, @Valid @RequestBody UpdateUserRequest request) {
        String usernameLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!usernameLogin.equals(user.getUsername())) throw new CustomException(ErrorCode.UNAUTHENTICATED);
        user = userMapper.updateUser(user, request);

        if(request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        UpdateUserResponse response = userMapper.toUpdateUserResponse(user);
        response.setRole(user.getRole().getRoleName());
        return response;
    }

    @Transactional
    public void softDeleteUser(Long userId) {
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        user.setDeleteFlag(true);
        userRepository.save(user);
    }

    public UserInfoResponse getUserLoginInfo() {
        String usernameLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User userLogin = userRepository.findByUsernameAndDeleteFlagIsFalse(usernameLogin).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserInfoResponse(userLogin);
    }




}
