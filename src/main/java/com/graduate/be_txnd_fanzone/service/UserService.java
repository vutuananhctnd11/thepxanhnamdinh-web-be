package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.user.CreateUserRequest;
import com.graduate.be_txnd_fanzone.dto.user.CreateUserResponse;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

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


}
