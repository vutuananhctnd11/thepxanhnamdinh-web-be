package com.graduate.be_txnd_fanzone.controller;

import com.graduate.be_txnd_fanzone.dto.ApiResponse;
import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse;
import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.dto.message.MessageResponse;
import com.graduate.be_txnd_fanzone.service.ConversationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    @GetMapping("/check-conversation")
    public ResponseEntity<ApiResponse<ConversationResponse>> checkIsExistsConversation (@RequestParam Long userId) {
        ApiResponse<ConversationResponse> apiResponse = new ApiResponse<>(conversationService.checkIsExistsConversation(userId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ConversationResponse>> getConversations (@RequestParam Long conversationId) {
        ApiResponse<ConversationResponse> apiResponse = new ApiResponse<>(conversationService.getConversationById(conversationId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ConversationResponse>>> listConversations () {
        ApiResponse<List<ConversationResponse>> apiResponse = new ApiResponse<>(conversationService.getConversation());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("old-message")
    public ResponseEntity<ApiResponse<PageableListResponse<MessageResponse>>> getOldMessages(@RequestParam int page,
                                                                                             @RequestParam int limit,
                                                                                             @RequestParam Long conversationId) {
        ApiResponse<PageableListResponse<MessageResponse>> apiResponse = new
                ApiResponse<>(conversationService.getOldMessages(page, limit, conversationId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
