package com.graduate.be_txnd_fanzone.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserResponse {

    String username;
    String fullName;
    String emailAddress;
    String role;
}
