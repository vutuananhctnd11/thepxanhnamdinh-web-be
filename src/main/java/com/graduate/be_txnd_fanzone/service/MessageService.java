package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.MessageMapper;
import com.graduate.be_txnd_fanzone.model.Conversation;
import com.graduate.be_txnd_fanzone.model.Message;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.ConversationRepository;
import com.graduate.be_txnd_fanzone.repository.MessageRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageService {

    MessageRepository messageRepository;
    UserRepository userRepository;
    ConversationRepository conversationRepository;
    MessageMapper messageMapper;

    public Message saveMessage(CreateMessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new CustomException(ErrorCode.CONVERSATION_NOT_FOUND));

        User sender = userRepository.findByUserIdAndDeleteFlagIsFalse(request.getSenderId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Message message = messageMapper.toMessage(request);
        message.setSender(sender);
        message.setConversation(conversation);
        message.setCreateAt(LocalDateTime.now());
        return messageRepository.save(message);
    }


}
