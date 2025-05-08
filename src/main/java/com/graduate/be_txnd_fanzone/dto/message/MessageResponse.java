package com.graduate.be_txnd_fanzone.dto.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {

    Long conversationId;
    Long senderId;
    String content;
    Byte type;
    String createAt;
}
