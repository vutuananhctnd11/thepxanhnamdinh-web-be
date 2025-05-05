package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse;
import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.model.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationService {

    SimpMessagingTemplate messagingTemplate;
    MessageService messageService;

    public void sendMessage(@Payload CreateMessageRequest request) {
        Message message = messageService.saveMessage(request);

        messagingTemplate.convertAndSend("/topic/chat/"+ request.getConversationId(), message);
    }

    public void authenticate (Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    public ConversationResponse createConversation (Long userId) {

    }
}
