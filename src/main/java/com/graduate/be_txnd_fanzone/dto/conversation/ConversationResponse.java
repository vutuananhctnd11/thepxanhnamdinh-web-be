package com.graduate.be_txnd_fanzone.dto.conversation;

import com.graduate.be_txnd_fanzone.dto.message.MessageResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponse {

    Long id;
    Long userId;
    String name;
    String avatar;
    Byte type;
    MessageResponse lastMessage;
}
