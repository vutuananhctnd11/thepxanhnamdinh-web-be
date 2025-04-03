package com.graduate.be_txnd_fanzone.dto.login;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    String username;
    String password;
}
