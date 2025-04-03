package com.graduate.be_txnd_fanzone.dto.login;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {

    Boolean authenticated;
    String token;
}
