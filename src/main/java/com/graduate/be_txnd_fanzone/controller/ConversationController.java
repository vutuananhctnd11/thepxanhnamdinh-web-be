package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.service.ConversationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationController {

    ConversationService conversationService;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload CreateMessageRequest request, Principal principal) {
        conversationService.authenticate(principal);
        conversationService.sendMessage(request);
    }

}
