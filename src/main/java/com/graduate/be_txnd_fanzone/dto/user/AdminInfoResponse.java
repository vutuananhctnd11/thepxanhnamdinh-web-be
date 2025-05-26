package com.graduate.be_txnd_fanzone.dto.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminInfoResponse {

    Long userId;
    String username;
    String firstName;
    String lastName;
    String avatar;
    Integer roleId;
}
