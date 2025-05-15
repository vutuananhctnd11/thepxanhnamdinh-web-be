package com.graduate.be_txnd_fanzone.service;

import com.graduate.be_txnd_fanzone.dto.PageableListResponse;
import com.graduate.be_txnd_fanzone.dto.conversation.ConversationResponse;
import com.graduate.be_txnd_fanzone.dto.message.CreateMessageRequest;
import com.graduate.be_txnd_fanzone.dto.message.MessageResponse;
import com.graduate.be_txnd_fanzone.enums.ErrorCode;
import com.graduate.be_txnd_fanzone.exception.CustomException;
import com.graduate.be_txnd_fanzone.mapper.ConversationMapper;
import com.graduate.be_txnd_fanzone.mapper.MessageMapper;
import com.graduate.be_txnd_fanzone.model.Conversation;
import com.graduate.be_txnd_fanzone.model.ConversationMember;
import com.graduate.be_txnd_fanzone.model.Message;
import com.graduate.be_txnd_fanzone.model.User;
import com.graduate.be_txnd_fanzone.repository.ConversationMemberRepository;
import com.graduate.be_txnd_fanzone.repository.ConversationRepository;
import com.graduate.be_txnd_fanzone.repository.MessageRepository;
import com.graduate.be_txnd_fanzone.repository.UserRepository;
import com.graduate.be_txnd_fanzone.util.SecurityUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
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
    MessageMapper messageMapper;
    MessageRepository messageRepository;
    ConversationMemberRepository conversationMemberRepository;

    public void sendMessage(@Payload CreateMessageRequest request) {
        Message message = messageService.saveMessage(request);

        if (request.getReplyToId() != null) {
            Message replyToMessage = messageRepository.findById(request.getReplyToId())
                    .orElseThrow(() -> new CustomException(ErrorCode.MESSAGE_NOT_FOUND));
            message.setReplyTo(replyToMessage);
            messageRepository.save(message);
        }
        MessageResponse messageResponse = messageMapper.toMessageResponse(message);
        messageResponse.setCreateAt(message.getCreateAt().format(DateTimeFormatter.ofPattern("HH:mm dd/MM")));
        List<ConversationMember> participants =  conversationMemberRepository.findAllByConversation_Id(request.getConversationId());

        for (ConversationMember participant : participants) {
            messagingTemplate.convertAndSendToUser(String.valueOf(participant.getUser().getUserId()), "/queue/chat", messageResponse);
        }
    }

    public void authenticate(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    public ConversationResponse checkIsExistsConversation(Long userId) {
        Long userLoginId = securityUtil.getCurrentUserId();
        Conversation conversation = conversationRepository
                .findPrivateConversationBetweenUsers(userId, userLoginId).orElse(null);
        User user = userRepository.findByUserIdAndDeleteFlagIsFalse(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (conversation == null) {
            conversation = new Conversation();
            conversation.setType((byte) 0);
            conversationRepository.save(conversation);

            conversationMemberService.create(userId, conversation.getId());
            conversationMemberService.create(userLoginId, conversation.getId());
        }
        ConversationResponse response = conversationMapper.toConversationResponse(conversation);
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setAvatar(user.getAvatar());
        response.setUserId(userId);
        return response;
    }

    public ConversationResponse getConversationById(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONVERSATION_NOT_FOUND));
        ConversationResponse response = conversationMapper.toConversationResponse(conversation);

        Long userLoginId = securityUtil.getCurrentUserId();;
        List<ConversationMember> members = conversation.getMembers();
        members.forEach(member -> {
            if (!member.getUser().getUserId().equals(userLoginId)){
                response.setUserId(member.getUser().getUserId());
                response.setFirstName(member.getUser().getFirstName());
                response.setLastName(member.getUser().getLastName());
                response.setAvatar(member.getUser().getAvatar());
            }
        });
        return response;
    }

    public List<ConversationResponse> getConversation() {
        Long userId = securityUtil.getCurrentUserId();
        return conversationRepository.findPrivateConversationsWithUsers(userId);
    }

    public PageableListResponse<MessageResponse> getOldMessages(int page, int limit, Long conversationId) {
        PageableListResponse<MessageResponse> response = new PageableListResponse<>();
        Pageable pageable = PageRequest.of(page - 1, limit);
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new CustomException(ErrorCode.CONVERSATION_NOT_FOUND));

        Page<Message> messageList = messageRepository.findByConversation_IdOrderByCreateAtDesc(conversation.getId(), pageable);
        List<MessageResponse> messageResponses = messageList.getContent().stream().map(message -> {
            MessageResponse messageResponse = messageMapper.toMessageResponse(message);
            messageResponse.setCreateAt(message.getCreateAt().format(DateTimeFormatter.ofPattern("HH:mm dd/MM")));
            return messageResponse;
        }).toList().reversed();
        response.setPage(page);
        response.setLimit(limit);
        response.setTotalPage((long) messageList.getTotalPages());
        response.setListResults(messageResponses);
        return response;
    }
}
