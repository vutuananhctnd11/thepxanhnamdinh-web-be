package com.graduate.be_txnd_fanzone.mapper;

import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.dto.message.MessageResponse;
import com.graduate.be_txnd_fanzone.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toMessage (CreateMessageRequest request);

    @Mapping(target = "senderId", source = "sender.userId")
    MessageResponse toMessageResponse (Message message);
}
