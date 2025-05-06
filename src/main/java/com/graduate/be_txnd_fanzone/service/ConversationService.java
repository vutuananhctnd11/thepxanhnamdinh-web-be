package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse;
import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.ConversationMapper;
import com.graduate.be_txnd_fanzone.model.Conversation;
import com.graduate.be_txnd_fanzone.model.ConversationMember;
import com.graduate.be_txnd_fanzone.model.Message;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.ConversationRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConversationService {

    SimpMessagingTemplate messagingTemplate;
    MessageService messageService;
    ConversationRepository conversationRepository;
    SecurityUtil securityUtil;
    ConversationMemberService conversationMemberService;
    ConversationMapper conversationMapper;
    UserRepository userRepository;

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
        Long userLoginId = securityUtil.getCurrentUserId();
        Conversation conversation = conversationRepository
                .findPrivateConversationBetweenUsers(userId, userLoginId).orElse(null);
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userLoginId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (conversation == null) {
            conversation = new Conversation();
            conversation.setType((byte) 0);
            conversationRepository.save(conversation);

            conversationMemberService.create(userId, conversation.getId());
            conversationMemberService.create(userLoginId, conversation.getId());
        }
        ConversationResponse response = conversationMapper.toConversationResponse(conversation);
        response.setName(user.getFirstName()+" "+user.getLastName());
        response.setAvatar(user.getAvatar());
        return response;
    }

    public List<ConversationResponse> getConversation () {
        Long userId = securityUtil.getCurrentUserId();
        List<ConversationMember> lisConversationMember = conversationMemberService.findByUserId(userId);
        return lisConversationMember.stream().map(conversationMember
                -> conversationMapper.toConversationResponse(conversationMember.getConversation())).toList();
    }
}
