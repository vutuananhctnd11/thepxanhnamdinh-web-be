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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;

    public CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = userMapper.toUser(request);
        Role role = roleRepository.findByRoleName(RoleEnums.USER.name()).orElseThrow(() ->
                new CustomException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(role);
        userRepository.save(user);
        CreateUserResponse response = userMapper.toCreateUserResponse(user);
        response.setRole(user.getRole().getRoleName());
        return response;
    }


}
