package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.user.*;
import com.graduate.be_txnd_fanzone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.lang.invoke.MethodHandleProxies;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User toUser(CreateUserRequest request);

    @Mapping(target = "username", ignore = true)
    User updateUser(@MappingTarget User user, UpdateUserRequest request);

    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "role", ignore = true)
    CreateUserResponse toCreateUserResponse (User user);

    @Mapping(target = "role", ignore = true)
    UpdateUserResponse toUpdateUserResponse (User user);

    UserInfoResponse toUserInfoResponse (User user);

    PersonalPageResponse toPersonalPageResponse (User user);

    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "emailAddress", ignore = true)
    @Mapping(target = "address", ignore = true)
    PersonalPageResponse toOtherUserPersonalPageResponse (User user);
}
