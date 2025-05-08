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
    String firstName;
    String lastName;
    String avatar;
    Byte type;
    MessageResponse lastMessage;

    public ConversationResponse(Long id, Long userId, String firstName, String lastName, String avatar, Byte type) {
        this.id = id;
        this.type = type;
        this.avatar = avatar;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userId = userId;
    }
}
