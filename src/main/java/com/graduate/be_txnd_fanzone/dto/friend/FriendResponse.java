package com.graduate.be_txnd_fanzone.dto.friend;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendResponse {

    Long friendId;
    Long senderId;
    String senderFirstName;
    String senderLastName;
    Long receiverId;
    String receiverFirstName;
    String receiverLastName;
    String seenAt;
}
