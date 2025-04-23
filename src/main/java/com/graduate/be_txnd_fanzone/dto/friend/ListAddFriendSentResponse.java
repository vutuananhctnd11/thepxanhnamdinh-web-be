package com.graduate.be_txnd_fanzone.dto.friend;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListAddFriendSentResponse {

    Long receiverId;
    String avatar;
    String fullName;
    String seenAt;
}
