package com.graduate.be_txnd_fanzone.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminCreateUserRequest {

    String username;
    String firstName;
    String lastName;
    Integer roleId;
    String password;
    String emailAddress;
    String avatar;

}
