package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse;
import com.graduate.be_txnd_fanzone.model.Conversation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    @Mapping(target = "userId", source = "user.userId")
    ConversationResponse toConversationResponse (Conversation conversation);
}
