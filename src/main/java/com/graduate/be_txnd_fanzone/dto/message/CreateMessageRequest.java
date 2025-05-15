package com.graduate.be_txnd_fanzone.dto.message;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMessageRequest {

    Long conversationId;
    Long senderId;
    String content;
    Byte type;
    Long replyToId;
}
