package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.dto.message.MessageResponse;
import com.graduate.be_txnd_fanzone.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toMessage (CreateMessageRequest request);

    @Mapping(target = "messageId", source = "id")
    @Mapping(target = "senderId", source = "sender.userId")
    @Mapping(target = "conversationId", source = "conversation.id")
    @Mapping(target = "replyToMessageId", source = "replyTo.id")
    @Mapping(target = "replyToMessageContent", source = "replyTo.content")
    @Mapping(target = "replyToMessageType", source = "replyTo.type")
    MessageResponse toMessageResponse (Message message);
}
