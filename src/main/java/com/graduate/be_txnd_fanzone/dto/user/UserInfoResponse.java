package com.graduate.be_txnd_fanzone.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {

    Long userId;
    String username;
    String firstName;
    String lastName;
    String avatar;
}
