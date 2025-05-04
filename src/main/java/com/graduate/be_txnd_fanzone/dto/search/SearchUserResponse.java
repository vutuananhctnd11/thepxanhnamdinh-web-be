package com.graduate.be_txnd_fanzone.dto.search;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchUserResponse {

    Long userId;
    String fullName;
    String avatar;
    Long totalFriends;
    Boolean isFriend;
    Boolean isSentRequest;
    Boolean isSender;
}
