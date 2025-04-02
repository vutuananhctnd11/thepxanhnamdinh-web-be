package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.user.CreateUserRequest;
import com.graduate.be_txnd_fanzone.dto.user.CreateUserResponse;
import com.graduate.be_txnd_fanzone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(CreateUserRequest request);

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "role", ignore = true)
    CreateUserResponse toCreateUserResponse (User user);
}
